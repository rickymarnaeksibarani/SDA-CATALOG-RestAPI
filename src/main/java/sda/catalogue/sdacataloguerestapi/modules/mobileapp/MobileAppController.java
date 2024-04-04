package sda.catalogue.sdacataloguerestapi.modules.mobileapp;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.ApiResponse;
import sda.catalogue.sdacataloguerestapi.modules.mobileapp.dto.MobileAppDto;
import sda.catalogue.sdacataloguerestapi.modules.mobileapp.dto.MobileAppResponseDto;
import sda.catalogue.sdacataloguerestapi.modules.mobileapp.dto.UserFilterRequest;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("api/v1/mobile-app")
public class MobileAppController {
    @Autowired
    private MobileAppService mobileAppService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<MobileAppResponseDto> createMobileApp(
            @RequestPart @Valid MobileAppDto request,
            @RequestPart(value = "documentation", required = false) List<MultipartFile> documentation,
            @RequestPart(value = "ipaFile", required = false) MultipartFile ipaFile,
            @RequestPart(value = "androidFile", required = false) MultipartFile androidFile
    ) throws Exception {
        if (Objects.nonNull(documentation)) {
            request.setDocumentation(documentation);
        }

        if (Objects.nonNull(ipaFile)) {
            request.setIpaFile(ipaFile);
        }

        if (Objects.nonNull(androidFile)) {
            request.setAndroidFile(androidFile);
        }

        MobileAppResponseDto mobileApp = mobileAppService.createMobileApp(request);
        return ApiResponse.<MobileAppResponseDto>builder()
                .result(mobileApp)
                .status(HttpStatus.CREATED)
                .message("Success create data")
                .build();
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<MobileAppResponseDto> updateMobileAppById(
        @PathVariable Long id,
        @RequestPart @Valid MobileAppDto request,
        @RequestPart(value = "documentation", required = false) List<MultipartFile> documentation,
        @RequestPart(value = "ipaFile", required = false) MultipartFile ipaFile,
        @RequestPart(value = "androidFile", required = false) MultipartFile androidFile
    ) throws Exception {
        if (Objects.nonNull(documentation)) {
            request.setDocumentation(documentation);
        }

        if (Objects.nonNull(ipaFile)) {
            request.setIpaFile(ipaFile);
        }

        if (Objects.nonNull(androidFile)) {
            request.setAndroidFile(androidFile);
        }

        MobileAppResponseDto mobileApp = mobileAppService.updateMobileApp(id, request);
        return ApiResponse.<MobileAppResponseDto>builder()
                .message("Success update data")
                .status(HttpStatus.OK)
                .result(mobileApp)
                .build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<Object> getAllMobileApp(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            UserFilterRequest filterRequest) {
        Object allMobileApp = mobileAppService.getAllMobileApp(page, size, filterRequest);

        return ApiResponse.builder()
                .result(allMobileApp)
                .status(HttpStatus.OK)
                .message("Get All Data")
                .build();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<MobileAppResponseDto> getMobileAppById(@PathVariable Long id) throws Exception {
        MobileAppResponseDto mobileApp = mobileAppService.getMobileAppById(id);
        return ApiResponse.<MobileAppResponseDto>builder()
                .message("Success get data")
                .status(HttpStatus.OK)
                .result(mobileApp)
                .build();
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<String> deleteById(@PathVariable Long id) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        mobileAppService.deleteById(id);
        return ApiResponse.<String>builder()
                .result("Deleted")
                .status(HttpStatus.NO_CONTENT)
                .message("Success delete data")
                .build();
    }
}
