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

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DashboardService {
    @Autowired
    private MobileAppRepository mobileAppRepository;
    @Autowired
    private WebAppRepository webAppRepository;
    @Autowired
    private SDAHostingRepository sdaHostingRepository;

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
        List<SDAHostingEntity> sdaHosting = sdaHostingRepository.findAll();

        List<StatisticByHostingDto> statsByHosting = new ArrayList<>();
        sdaHosting.forEach(data -> {
            StatisticByHostingDto statsByHostingData = new StatisticByHostingDto();
            statsByHostingData.setName(data.getSdaHosting());
            statsByHostingData.setTotal(webAppRepository.countBySdaHosting(data.getSdaHosting()));
            statsByHosting.add(statsByHostingData);
        });

        return statsByHosting;
    }
}
