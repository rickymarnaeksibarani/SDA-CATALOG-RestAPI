package sda.catalogue.sdacataloguerestapi.modules.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.ApiResponse;
import sda.catalogue.sdacataloguerestapi.modules.dashboard.dto.StatisticByHostingDto;
import sda.catalogue.sdacataloguerestapi.modules.dashboard.dto.StatisticStatusResponseDto;

import java.util.List;

@RestController
@RequestMapping("api/v1/dashboard")
//@CrossOrigin("${spring.frontend}")
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;

    @GetMapping(value = "stats-by-status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<StatisticStatusResponseDto> statsByStatus() {
        StatisticStatusResponseDto statisticStatusResponse = dashboardService.statisticSdaByStatus();

        return ApiResponse.<StatisticStatusResponseDto>builder()
                .status(HttpStatus.OK)
                .message("Success get statistic by status")
                .result(statisticStatusResponse)
                .build();
    }

    @GetMapping(value = "stats-by-hosting", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<List<StatisticByHostingDto>> statsByHosting() {
        List<StatisticByHostingDto> statisticByHosting = dashboardService.statisticByHosting();

        return ApiResponse.<List<StatisticByHostingDto>>builder()
                .result(statisticByHosting)
                .status(HttpStatus.OK)
                .message("Success get statistic by hosting")
                .build();
    }
}
