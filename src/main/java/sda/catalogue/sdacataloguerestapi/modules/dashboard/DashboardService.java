package sda.catalogue.sdacataloguerestapi.modules.dashboard;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sda.catalogue.sdacataloguerestapi.core.enums.Status;
import sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Entities.SDAHostingEntity;
import sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Repositories.SDAHostingRepository;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Repositories.WebAppRepository;
import sda.catalogue.sdacataloguerestapi.modules.dashboard.dto.StatisticByHostingDto;
import sda.catalogue.sdacataloguerestapi.modules.dashboard.dto.StatisticStatusResponseDto;
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
    SDAHostingRepository sdaHostingRepository;

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

        List<StatisticByHostingDto> webAppStatData = webAppStats.stream().map(val -> {
            Optional<SDAHostingEntity> hostingData = sdaHostingRepository.findById(Long.parseLong((String) val[0]));

            StatisticByHostingDto hostingStat = new StatisticByHostingDto();
            hostingStat.setName(hostingData.get().getSdaHosting());
            hostingStat.setTotal((Long) val[1]);
            return hostingStat;
        }).toList();

        List<StatisticByHostingDto> mobileAppStatData = mobileAppStats.stream().map(val -> {
            StatisticByHostingDto hostingStat = new StatisticByHostingDto();
            hostingStat.setName((String) val[0]);
            hostingStat.setTotal((Long) val[1]);
            return hostingStat;
        }).toList();

        List<StatisticByHostingDto> statsByHosting = new ArrayList<>();
        statsByHosting.addAll(webAppStatData);
        statsByHosting.addAll(mobileAppStatData);
        log.info("statsByHosting {}", statsByHosting);

        return statsByHosting;
    }
}
