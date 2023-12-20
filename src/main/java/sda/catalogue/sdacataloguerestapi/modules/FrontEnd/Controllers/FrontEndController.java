package sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.ApiResponse;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.PaginateResponse;
import sda.catalogue.sdacataloguerestapi.core.Exception.CustomRequestException;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Dto.FrontEndDTO;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Entities.FrontEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Services.FrontEndService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/front-end")
public class FrontEndController {
    @Autowired
    private FrontEndService frontEndService;

    @GetMapping()
    public ResponseEntity<?> searchFrontEnd(
            @RequestParam(name = "searchTerm", defaultValue = "") String searchTerm,
            @RequestParam(name = "page", defaultValue = "1") long page,
            @RequestParam(name = "size", defaultValue = "10") long size
    ) {
        try {
            PaginateResponse<List<FrontEndEntity>> result = frontEndService.searchFrontEnd(searchTerm, page, size);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, "Success retrieved data front end!", result), HttpStatus.OK);
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getFrontEndByUuid(
            @PathVariable("uuid") UUID uuid
    ) {
        try {
            FrontEndEntity result = frontEndService.getFrontEndByUuid(uuid);
            ApiResponse<FrontEndEntity> response = new ApiResponse<>(HttpStatus.OK, "Success retrieved data front end!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }


    @PostMapping()
    public ResponseEntity<?> createFrontEnd(
            @Valid @RequestBody FrontEndDTO request
    ) {
        try {
            FrontEndEntity result = frontEndService.createFrontEnd(request);
            ApiResponse<FrontEndEntity> response = new ApiResponse<>(HttpStatus.CREATED, "Success create data front end!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> updateFrontEnd(
            @PathVariable("uuid") UUID uuid,
            @Valid @RequestBody FrontEndDTO request
    ) {
        try {
            FrontEndEntity result = frontEndService.updateFrontEnd(uuid, request);
            ApiResponse<FrontEndEntity> response = new ApiResponse<>(HttpStatus.ACCEPTED, "Success update data front end!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }


    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteFrontEnd(
            @PathVariable("uuid") UUID uuid
    ) {
        try {
            FrontEndEntity result = frontEndService.deleteFrontEnd(uuid);
            ApiResponse<FrontEndEntity> response = new ApiResponse<>(HttpStatus.OK, "Success delete data front end!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }
}
