package sda.catalogue.sdacataloguerestapi.modules.dashboard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sda.catalogue.sdacataloguerestapi.core.enums.BusinessImpactPriority;
import sda.catalogue.sdacataloguerestapi.core.enums.Status;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Entities.DinasEntity;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Entities.MappingFunctionEntity;
import sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Entities.SDAHostingEntity;
import sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Repositories.SDAHostingRepository;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.WebAppEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Repositories.WebAppRepository;
import sda.catalogue.sdacataloguerestapi.modules.dashboard.dto.ListAllSdaDto;
import sda.catalogue.sdacataloguerestapi.modules.dashboard.dto.PagingRequest;
import sda.catalogue.sdacataloguerestapi.modules.dashboard.dto.StatisticByHostingDto;
import sda.catalogue.sdacataloguerestapi.modules.dashboard.dto.StatisticStatusResponseDto;
import sda.catalogue.sdacataloguerestapi.modules.mobileapp.dto.ApplicationUrlDto;
import sda.catalogue.sdacataloguerestapi.modules.mobileapp.entity.MobileAppEntity;
import sda.catalogue.sdacataloguerestapi.modules.mobileapp.repository.MobileAppRepository;

import java.util.*;

@Slf4j
@Service
public class DashboardService {
    @Autowired
    private MobileAppRepository mobileAppRepository;
    @Autowired
    private WebAppRepository webAppRepository;
    @Autowired
    private SDAHostingRepository sdaHostingRepository;
    @Autowired
    private ObjectMapper objectMapper;


    @Transactional(readOnly = true)
    public StatisticStatusResponseDto statisticSdaByStatus() {
        Integer active = mobileAppRepository.countAllByStatus(Status.ACTIVE);
        Integer inactive = mobileAppRepository.countAllByStatus(Status.INACTIVE);
        Integer underConstruction = mobileAppRepository.countAllByStatus(Status.UNDER_CONSTRUCTION);
        Integer underReview = mobileAppRepository.countAllByStatus(Status.UNDER_REVIEW);

        active += webAppRepository.countAllByStatus("Active");
        inactive += webAppRepository.countAllByStatus("Inactive");
        underConstruction += webAppRepository.countAllByStatus("Under Construction");
        underReview += webAppRepository.countAllByStatus("Under Review");

        return StatisticStatusResponseDto.builder()
                .underConstruction(underConstruction)
                .inActive(inactive)
                .active(active)
                .underReview(underReview)
                .build();
    }

    @Transactional(readOnly = true)
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
            Optional<SDAHostingEntity> hostingData = sdaHostingRepository.findById(Long.parseLong((String) val[0]));

            StatisticByHostingDto hostingStat = new StatisticByHostingDto();
            hostingStat.setName(hostingData.get().getSdaHosting());
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
    public Page<ListAllSdaDto> getAllSdaData(PagingRequest pagingRequest) {
        String order = Objects.nonNull(pagingRequest.getOrder()) ? pagingRequest.getOrder() : "DESC";
        String orderBy = Objects.nonNull(pagingRequest.getOrderBy()) ? pagingRequest.getOrderBy() : "createdAt";
        Integer size = pagingRequest.getSize();
        Integer page = pagingRequest.getPage();
        size = (int) (size % 2 == 0 ? (double) (size / 2) : size / 2);

        Sort.Order newOrder = Objects.equals(order, "ASC") ? Sort.Order.asc(orderBy) : Sort.Order.desc(orderBy);

        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(newOrder));
        Page<MobileAppEntity> allMobileApps = mobileAppRepository.findAll(specification(pagingRequest), pageRequest);
        Page<WebAppEntity> allWebApps = webAppRepository.findAll(specification(pagingRequest), pageRequest);

        List<ListAllSdaDto> mobileAppList = allMobileApps.getContent().stream().map(data -> {
            List<String> mappingFunction;
            List<String> role;
            ApplicationUrlDto address;
            List<String> department;

            try {
                mappingFunction = objectMapper.readValue(data.getMappingFunction(), new TypeReference<>() {});
                role = objectMapper.readValue(data.getRole(), new TypeReference<>() {});
                address = objectMapper.readValue(data.getApplicationUrl(), ApplicationUrlDto.class);
                department = objectMapper.readValue(data.getDepartment(), new TypeReference<>() {});
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            ListAllSdaDto allSdaDto = new ListAllSdaDto();
            allSdaDto.setId(data.getId());
            allSdaDto.setMappingFunction(mappingFunction);
            allSdaDto.setRole(role);
            allSdaDto.setAddress(List.of(address.getAppstoreUrl(), address.getPlaystoreUrl()));
            allSdaDto.setStatus(data.getStatus());
            allSdaDto.setDepartment(department);
            allSdaDto.setName(data.getApplicationName());
            return allSdaDto;
        }).toList();

        List<ListAllSdaDto> webAppList = allWebApps.getContent().stream().map(data -> {
            List<MappingFunctionEntity> mappingFunction = data.getMappingFunctionList().stream().toList();
            List<List<DinasEntity>> departmentList = mappingFunction.stream()
                    .map(MappingFunctionEntity::getDinasEntityList)
                    .toList();
            List<List<String>> department = departmentList
                    .stream()
                    .map(item -> item.stream()
                            .map(DinasEntity::getDinas)
                            .toList())
                    .toList();

            ListAllSdaDto allSdaDto = new ListAllSdaDto();
            allSdaDto.setId(data.getIdWebapp());
            allSdaDto.setRole(null);
            allSdaDto.setDepartment(department.get(0));
            allSdaDto.setAddress(List.of(data.getAddress()));
            allSdaDto.setName(data.getApplicationName());
            allSdaDto.setStatus(Status.valueOf(data.getStatus().toUpperCase()));
            allSdaDto.setMappingFunction(mappingFunction.stream().map(MappingFunctionEntity::getMappingFunction).toList());
            return allSdaDto;
        }).toList();

        List<ListAllSdaDto> listAllSda = new ArrayList<>();
        listAllSda.addAll(mobileAppList);
        listAllSda.addAll(webAppList);

        return new PageImpl<>(listAllSda, pageRequest, allMobileApps.getTotalElements() + allWebApps.getTotalElements());
    }

    private <T> Specification<T> specification(PagingRequest request) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(request.getSearch())) {
                predicates.add(
                        builder.like(builder.upper(root.get("applicationName")), "%" + request.getSearch().toUpperCase() + "%")
                );
            }

            if (Objects.nonNull(request.getAdvanceFilter().getMappingFunction())) {
                List<String> mappingFunction = request.getAdvanceFilter().getMappingFunction();
                Expression delimiter = builder.literal(",");

                predicates.add(
                        builder.like(
                                builder.function("array_to_string", String.class, root.get("mappingFunction"), delimiter),
                                "%" + mappingFunction + "%"
                        )
                );
//                predicates.add(builder.equal(root.get("mappingFunction"), mappingFunction));
            }

            if (Objects.nonNull(request.getAdvanceFilter().getDepartment())) {
                List<String> department = request.getAdvanceFilter().getDepartment();
                predicates.add(builder.equal(root.get("department"), department));
            }

            if (Objects.nonNull(request.getAdvanceFilter().getRole())) {
                List<String> role = request.getAdvanceFilter().getRole();
                predicates.add(builder.equal(root.get("role"), role));
            }

//            if (Objects.nonNull(request.getFilterByTech())) {
//                predicates.add(
//                        builder.or(
//                                builder.equal(root.get("frontEndList"), request.getFilterByTech()),
//                                builder.equal(root.get("backendList"), request.getFilterByTech())
//                        )
//                );
//            }

            if (Objects.nonNull(request.getAdvanceFilter().getBusinessImpactPriority())) {
                BusinessImpactPriority businessImpactPriority = request.getAdvanceFilter().getBusinessImpactPriority();
                predicates.add(
                        builder.equal(builder.upper(root.get("businessImpactPriority").as(String.class)), businessImpactPriority.name().toUpperCase())
                );
            }

            if (Objects.nonNull(request.getAdvanceFilter().getStatus())) {
                Status status = request.getAdvanceFilter().getStatus();
                predicates.add(builder.equal(root.get("status").as(String.class), status.name()));
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
    }
}
