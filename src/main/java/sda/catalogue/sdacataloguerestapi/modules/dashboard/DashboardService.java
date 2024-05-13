package sda.catalogue.sdacataloguerestapi.modules.dashboard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sda.catalogue.sdacataloguerestapi.core.enums.BusinessImpactPriority;
import sda.catalogue.sdacataloguerestapi.core.enums.Status;
import sda.catalogue.sdacataloguerestapi.core.utils.PaginationUtil;
//import sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Entities.SDAHostingEntity;
//import sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Repositories.SDAHostingRepository;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Repositories.WebAppRepository;
import sda.catalogue.sdacataloguerestapi.modules.dashboard.dto.PagingRequest;
import sda.catalogue.sdacataloguerestapi.modules.dashboard.dto.StatisticByHostingDto;
import sda.catalogue.sdacataloguerestapi.modules.dashboard.dto.StatisticStatusResponseDto;
import sda.catalogue.sdacataloguerestapi.modules.dashboard.entity.DashboardEntity;
import sda.catalogue.sdacataloguerestapi.modules.dashboard.repository.AllSdaRepository;
import sda.catalogue.sdacataloguerestapi.modules.mobileapp.repository.MobileAppRepository;

import java.util.*;

@Slf4j
@Service
public class DashboardService {
    @Autowired
    private MobileAppRepository mobileAppRepository;
    @Autowired
    private WebAppRepository webAppRepository;
//    @Autowired
//    private SDAHostingRepository sdaHostingRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AllSdaRepository sdaRepository;

    @Transactional(readOnly = true)
//    @Cacheable(value = "statsByStatus")
    public StatisticStatusResponseDto statisticSdaByStatus() {
        Integer active = mobileAppRepository.countAllByStatus(Status.ACTIVE);
        Integer inactive = mobileAppRepository.countAllByStatus(Status.INACTIVE);
        Integer underConstruction = mobileAppRepository.countAllByStatus(Status.UNDER_CONSTRUCTION);
        Integer underReview = mobileAppRepository.countAllByStatus(Status.UNDER_REVIEW);

        active += webAppRepository.countAllByStatus(Status.ACTIVE);
        inactive += webAppRepository.countAllByStatus(Status.INACTIVE);
        underConstruction += webAppRepository.countAllByStatus(Status.UNDER_CONSTRUCTION);
        underReview += webAppRepository.countAllByStatus(Status.UNDER_REVIEW);

        return StatisticStatusResponseDto.builder()
                .underConstruction(underConstruction)
                .inActive(inactive)
                .active(active)
                .underReview(underReview)
                .build();
    }

    @Transactional(readOnly = true)
//    @Cacheable("statsByHosting")
    public List<StatisticByHostingDto> statisticByHosting() {
        List<Object[]> mobileAppStats = mobileAppRepository.countAllBySdaHosting();
        List<Object[]> webAppStats = webAppRepository.countAllBySdaHosting();

        List<StatisticByHostingDto> mobileAppStatData = mobileAppStats.stream().map(val -> {
            StatisticByHostingDto hostingStat = new StatisticByHostingDto();
            hostingStat.setName((String) val[0]);
            hostingStat.setTotal((Long) val[1]);
            return hostingStat;
        }).toList();

        List<StatisticByHostingDto> webAppStatData = webAppStats.stream().map(val -> {
//            Optional<SDAHostingEntity> hostingData = sdaHostingRepository.findById((Long) val[0]);

            StatisticByHostingDto hostingStat = new StatisticByHostingDto();
            hostingStat.setName((String) val[0]);
            hostingStat.setTotal((Long) val[1]);
            return hostingStat;
        }).toList();

        List<StatisticByHostingDto> statsByHosting = new ArrayList<>();
        statsByHosting.addAll(webAppStatData);
        statsByHosting.addAll(mobileAppStatData);

        Map<String, Integer> mergedData = new HashMap<>();

        for (StatisticByHostingDto data : statsByHosting) {
            if (mergedData.containsKey(data.getName())) {
                mergedData.put(data.getName(), (int) (mergedData.get(data.getName()) + data.getTotal()));
            } else {
                mergedData.put(data.getName(), Math.toIntExact(data.getTotal()));
            }
        }

        statsByHosting.clear();
        mergedData.forEach((key, value) -> {
            StatisticByHostingDto hostingDto = new StatisticByHostingDto();
            hostingDto.setName(key);
            hostingDto.setTotal(Long.valueOf(value));
            statsByHosting.add(hostingDto);
        });

        return statsByHosting;
    }

    @Transactional(readOnly = true)
    public PaginationUtil<DashboardEntity, DashboardEntity> getAllSdaData(PagingRequest pagingRequest) {
        String order = Objects.nonNull(pagingRequest.getOrder()) ? pagingRequest.getOrder() : "DESC";
        String orderBy = Objects.nonNull(pagingRequest.getOrderBy()) ? pagingRequest.getOrderBy() : "createdAt";
        Integer size = pagingRequest.getSize();
        Integer page = pagingRequest.getPage();

        Sort.Order newOrder = Objects.equals(order, "ASC") ? Sort.Order.asc(orderBy) : Sort.Order.desc(orderBy);
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(newOrder));
        Page<DashboardEntity> allSda = sdaRepository.findAll(specification(pagingRequest, "web"), pageRequest);

        return new PaginationUtil<>(allSda, DashboardEntity.class);
    }

    private <T> Specification<T> specification(PagingRequest request, String category) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(request.getFilterByTech()) && !request.getFilterByTech().isEmpty()) {
                List<String> filterByTech = request.getFilterByTech();

                if (category.equals("mobile")) {
                        predicates.add(
                                builder.or(
                                    builder.in(root.get("frontEnds").get("frontEnd")).value(filterByTech),
                                    builder.in(root.get("backEnds").get("backEnd")).value(filterByTech)
                                )
                        );
                } else if (category.equals("web")) {
                    predicates.add(
                            builder.or(
                                    builder.in(root.get("frontEndList").get("frontEnd")).value(filterByTech),
                                    builder.in(root.get("backEndList").get("backEnd")).value(filterByTech)
                            )
                    );
                }
            }

            if (Objects.nonNull(request.getSearch())) {
                predicates.add(
                        builder.like(builder.upper(root.get("applicationName")), "%" + request.getSearch().toUpperCase() + "%")
                );
            }

            if (Objects.nonNull(request.getMappingFunction()) && !request.getMappingFunction().isEmpty()) {
                List<String> mappingFunction = request.getMappingFunction();

                if (category.equals("mobile")) {
                        predicates.add(
                                builder.in(root.get("mappingFunction")).value(mappingFunction)
//                                builder.in(root.get("mappingFunctions").get("mappingFunction")).value(mappingFunction)
                        );
                } else if (category.equals("web")) {
                    predicates.add(
                            builder.in(root.get("mappingFunction")).value(mappingFunction)
//                            builder.in(root.get("mappingFunctionList").get("mappingFunction")).value(mappingFunction)
                    );
                }
            }

            if (Objects.nonNull(request.getDepartment()) && !request.getDepartment().isEmpty()) {
                List<String> department = request.getDepartment();

                if (category.equals("mobile")) {
                    predicates.add(
                            builder.in(root.get("mappingFunctions").get("dinasEntityList").get("dinas")).value(department)
                    );
                } else if (category.equals("web")) {
                    predicates.add(
                            builder.in(root.get("mappingFunctionList").get("dinasEntityList").get("dinas")).value(department)
                    );
                }
            }

            if (Objects.nonNull(request.getRole()) && !request.getRole().isEmpty()) {
                try {
                    List<String> role = request.getRole();

                    if (category.equals("mobile")) {
                            predicates.add(
                                    builder.like(builder.upper(root.get("role")), "%" + objectMapper.writeValueAsString(role).toUpperCase() + "%")
                            );
                    }
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }

            if (Objects.nonNull(request.getBusinessImpactPriority())) {
                BusinessImpactPriority businessImpactPriority = request.getBusinessImpactPriority();

                predicates.add(
                        builder.equal(builder.upper(root.get("businessImpactPriority")), businessImpactPriority.name().toUpperCase())
                );
            }

            if (Objects.nonNull(request.getStatus()) && !request.getStatus().isEmpty()) {
                predicates.add(
                        builder.in(root.get("status")).value(request.getStatus())
                );
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
    }
}
