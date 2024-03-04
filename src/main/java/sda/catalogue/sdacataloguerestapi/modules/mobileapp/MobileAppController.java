package sda.catalogue.sdacataloguerestapi.modules.mobileapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.ApiResponse;
import sda.catalogue.sdacataloguerestapi.core.utils.PaginationUtil;
import sda.catalogue.sdacataloguerestapi.modules.mobileapp.dto.MobileAppDto;
import sda.catalogue.sdacataloguerestapi.modules.mobileapp.entity.MobileAppEntity;


@Slf4j
@RestController
@RequestMapping("api/v1/mobile-app")
public class MobileAppController {
    @Autowired
    private MobileAppService mobileAppService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<MobileAppEntity> createMobileApp(@Valid @ModelAttribute MobileAppDto request) throws Exception {
        MobileAppEntity mobileApp = mobileAppService.createMobileApp(request);
        return ApiResponse.<MobileAppEntity>builder()
                .result(mobileApp)
                .status(HttpStatus.CREATED)
                .message("Success create data")
                .build();
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<MobileAppEntity> updateMobileAppById(@PathVariable Long id, @ModelAttribute @Valid MobileAppDto request) throws Exception {
        MobileAppEntity mobileApp = mobileAppService.updateMobileApp(id, request);
        return ApiResponse.<MobileAppEntity>builder()
                .message("Success update data")
                .status(HttpStatus.OK)
                .result(mobileApp)
                .build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<PaginationUtil> getAllMobileApp(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer perPage, @RequestParam(defaultValue = "") String search) {
        Object allMobileApp = mobileAppService.getAllMobileApp(page, perPage, search);
        return ApiResponse.<PaginationUtil>builder()
                .result((PaginationUtil) allMobileApp)
                .status(HttpStatus.OK)
                .message("Get All Data")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<MobileAppEntity> getMobileAppById(@PathVariable Long id) {
        MobileAppEntity mobileApp = mobileAppService.getMobileAppById(id);
        return ApiResponse.<MobileAppEntity>builder()
                .message("Success get data")
                .status(HttpStatus.OK)
                .result(mobileApp)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteById(@PathVariable Long id) throws JsonProcessingException {
        mobileAppService.deleteById(id);
        return ApiResponse.<String>builder()
                .result("Deleted")
                .status(HttpStatus.NO_CONTENT)
                .message("Success delete data")
                .build();
    }
}
