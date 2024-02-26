package sda.catalogue.sdacataloguerestapi.modules.BackEnd.Controllers;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.ApiResponse;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.PaginateResponse;
import sda.catalogue.sdacataloguerestapi.core.Exception.CustomRequestException;
import sda.catalogue.sdacataloguerestapi.core.utils.PaginationUtil;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Dto.BackEndDTO;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Entities.BackEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Services.BackEndService;

import java.util.List;
import java.util.UUID;
@Slf4j
@RestController
@RequestMapping("/api/v1/back-end")
@CrossOrigin(origins = "${spring.frontend}")
public class BackEndController {
    @Autowired
    private BackEndService backEndService;

    @GetMapping()
    public ResponseEntity<?> searchBackEnd(@ModelAttribute BackEndDTO searchDTO,
                                           @RequestParam("page") String page,
                                           @RequestParam("size") String size) {
        try {
            PaginationUtil<BackEndEntity, BackEndDTO> result = backEndService.getAllBackendByPagination(Integer.parseInt(page), Integer.parseInt(size));
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, "Success retrieved data back end!", result), HttpStatus.OK);
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getBackendByUuid(
            @PathVariable("uuid") UUID uuid
    ) {
        try {
            BackEndEntity result = backEndService.getBackEndByUuid(uuid);
            ApiResponse<BackEndEntity> response = new ApiResponse<>(HttpStatus.OK, "Success retrieved data back end!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @PostMapping()
    public ResponseEntity<?> createBackEnd(
            @Valid @RequestBody BackEndDTO request
    ) {
        try {
            BackEndEntity result = backEndService.createBackend(request);
            ApiResponse<BackEndEntity> response = new ApiResponse<>(HttpStatus.CREATED, "Success create data back end!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> updateBackEnd(
            @PathVariable("uuid") UUID uuid,
            @Valid @RequestBody BackEndDTO request
    ) {
        try {
            BackEndEntity result = backEndService.updateBackend(uuid, request);
            ApiResponse<BackEndEntity> response = new ApiResponse<>(HttpStatus.ACCEPTED, "Success update data back end!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteBackEnd(
            @PathVariable("uuid") UUID uuid
    ) {
        try {
            BackEndEntity result = backEndService.deleteBackend(uuid);
            ApiResponse<BackEndEntity> response = new ApiResponse<>(HttpStatus.OK, "Success delete data back end!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }
}
