package sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.ApiResponse;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.PaginateResponse;
import sda.catalogue.sdacataloguerestapi.core.Exception.CustomRequestException;
import sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Dto.SDAHostingDTO;
import sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Entities.SDAHostingEntity;
import sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Services.SDAHostingService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sda-hosting")
public class SDAHostingController {
    @Autowired
    private SDAHostingService sdaHostingService;

    @GetMapping()
    public ResponseEntity<?> searchSDAHosting(
            @RequestParam(name = "searchTerm", defaultValue = "") String searchTerm,
            @RequestParam(name = "page", defaultValue = "1") long page,
            @RequestParam(name = "size", defaultValue = "10") long size
    ) {
        try {
            PaginateResponse<List<SDAHostingEntity>> result = sdaHostingService.searchAndPaginate(searchTerm, page, size);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, "Success retrieved data sda hosting!", result), HttpStatus.OK);
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getSDAHostingByUuid(
            @PathVariable("uuid") UUID uuid
    ) {
        try {
            SDAHostingEntity result = sdaHostingService.getSDAHostingByUuid(uuid);
            ApiResponse<SDAHostingEntity> response = new ApiResponse<>(HttpStatus.OK, "Success retrieved data sda hosting!!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @PostMapping()
    public ResponseEntity<?> createSDAHosting(
            @Valid @RequestBody SDAHostingDTO request
    ) {
        try {
            SDAHostingEntity result = sdaHostingService.createSDAHosting(request);
            ApiResponse<SDAHostingEntity> response = new ApiResponse<>(HttpStatus.CREATED, "Success create data sda hosting!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> updateSDAHosting(
            @PathVariable("uuid") UUID uuid,
            @Valid @RequestBody SDAHostingDTO request
    ) {
        try {
            SDAHostingEntity result = sdaHostingService.updateSDAHosting(uuid, request);
            ApiResponse<SDAHostingEntity> response = new ApiResponse<>(HttpStatus.ACCEPTED, "Success update data sda hosting!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteSDAHosting(
            @PathVariable("uuid") UUID uuid
    ) {
        try {
            SDAHostingEntity result = sdaHostingService.deleteSDAHosting(uuid);
            ApiResponse<SDAHostingEntity> response = new ApiResponse<>(HttpStatus.OK, "Success delete data sda hosting!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }
}
