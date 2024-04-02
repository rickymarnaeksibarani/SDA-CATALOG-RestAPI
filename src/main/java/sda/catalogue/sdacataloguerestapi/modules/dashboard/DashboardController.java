package sda.catalogue.sdacataloguerestapi.modules.dashboard;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.ApiResponse;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.PagingResponse;
import sda.catalogue.sdacataloguerestapi.modules.dashboard.dto.ListAllSdaDto;
import sda.catalogue.sdacataloguerestapi.modules.dashboard.dto.PagingRequest;
import sda.catalogue.sdacataloguerestapi.modules.dashboard.dto.StatisticByHostingDto;
import sda.catalogue.sdacataloguerestapi.modules.dashboard.dto.StatisticStatusResponseDto;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/dashboard")
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

    @GetMapping(value = "get-all-sda", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<Object> getAllSdaApp(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            PagingRequest params
    ) {
        PagingRequest pagingRequest = PagingRequest.builder()
                .orderBy(params.getOrderBy())
                .order(params.getOrder())
                .page(page)
                .size(size)
                .search(params.getSearch())
                .role(params.getRole())
                .mappingFunction(params.getMappingFunction())
                .businessImpactPriority(params.getBusinessImpactPriority())
                .department(params.getDepartment())
                .status(params.getStatus())
                .filterByTech(params.getFilterByTech())
                .build();

        Page<ListAllSdaDto> allSdaData = dashboardService.getAllSdaData(pagingRequest);
        Integer totalPage = (int) Math.ceil(allSdaData.getTotalPages() / allSdaData.getSize());

        return ApiResponse.builder()
                .message("Success get data")
                .status(HttpStatus.OK)
                .paging(PagingResponse.builder()
                        .totalPage(totalPage)
                        .currentPage(allSdaData.getNumber() + 1)
                        .totalItems(allSdaData.getTotalElements())
                        .build())
                .result(allSdaData.getContent())
                .build();
    }
}
