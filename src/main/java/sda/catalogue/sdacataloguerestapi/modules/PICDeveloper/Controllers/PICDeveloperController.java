package sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sda.catalogue.sdacataloguerestapi.core.TangerangResponse.ApiResponse;
import sda.catalogue.sdacataloguerestapi.core.TangerangResponse.PaginateResponse;
import sda.catalogue.sdacataloguerestapi.core.TangerangValidation.TangerangRequestException;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Dto.PICDeveloperDTO;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Entities.PICDeveloperEntity;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Services.PICDeveloperService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/pic-developer")
public class PICDeveloperController {
    @Autowired
    private PICDeveloperService picDeveloperService;

    //Getting data PIC Developer with search and pagination
    @GetMapping()
    public ResponseEntity<?> searchPICDeveloper(
            @RequestParam(name = "searchTerm", defaultValue = "") String searchTerm,
            @RequestParam(name = "page", defaultValue = "1") long page,
            @RequestParam(name = "size", defaultValue = "10") long size
    ) {
        try {
            PaginateResponse<List<PICDeveloperEntity>> result = picDeveloperService.searchAndPaginate(searchTerm, page, size);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, "Successfully retrieved data pic developers!", result), HttpStatus.OK);
        } catch (TangerangRequestException error) {
            return error.GlobalTangerangRequestException(error.getMessage(), error.getStatus());
        }
    }

    //Getting data PIC Developer by UUID
    @GetMapping("/{uuid}")
    public ResponseEntity<?> getPICDeveloperByUuid(
            @PathVariable("uuid") UUID uuid
    ) {
        try {
            PICDeveloperEntity result = picDeveloperService.getPICDeveloperByUUID(uuid);
            ApiResponse<PICDeveloperEntity> response = new ApiResponse<>(HttpStatus.OK, "Success retrieved data pic developer!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (TangerangRequestException error) {
            return error.GlobalTangerangRequestException(error.getMessage(), error.getStatus());
        }
    }

    //Creating data PIC Developer
    @PostMapping()
    public ResponseEntity<?> createPICDeveloper(
            @RequestBody PICDeveloperDTO request
    ) {
        try {
            PICDeveloperEntity result = picDeveloperService.createPICDeveloper(request);
            ApiResponse<PICDeveloperEntity> response = new ApiResponse<>(HttpStatus.OK, "Success created data pic developer!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (TangerangRequestException error) {
            return error.GlobalTangerangRequestException(error.getMessage(), error.getStatus());
        }
    }

    //Updating data PIC Developer By UUID
    @PutMapping("/{uuid}")
    public ResponseEntity<?> updatePICDeveloper(
            @PathVariable("uuid") UUID uuid,
            @RequestBody PICDeveloperDTO request
    ) {
        try {
            PICDeveloperEntity result = picDeveloperService.updatePICDeveloper(uuid, request);
            ApiResponse<PICDeveloperEntity> response = new ApiResponse<>(HttpStatus.ACCEPTED, "Success updated data pic developer!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (TangerangRequestException error) {
            return error.GlobalTangerangRequestException(error.getMessage(), error.getStatus());
        }
    }

    //Deleting data PIC Developer By UUID
    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deletePICDeveloperByUuid(
            @PathVariable("uuid") UUID uuid
    ) {
        try {
            PICDeveloperEntity result = picDeveloperService.deletePICDeveloperByUuid(uuid);
            ApiResponse<PICDeveloperEntity> response = new ApiResponse<>(HttpStatus.OK, "Success deleted data pic developer!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (TangerangRequestException error) {
            return error.GlobalTangerangRequestException(error.getMessage(), error.getStatus());
        }
    }
}
