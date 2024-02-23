package sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Controllers;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.ApiResponse;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.PaginateResponse;
import sda.catalogue.sdacataloguerestapi.core.Exception.CustomRequestException;
import sda.catalogue.sdacataloguerestapi.core.utils.PaginationUtil;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Dto.BackEndDTO;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Entities.BackEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Dto.PICDeveloperDTO;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Entities.PICDeveloperEntity;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Services.PICDeveloperService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/pic-developer")
@CrossOrigin(origins = "${spring.frontend}")
public class PICDeveloperController {
    @Autowired
    private PICDeveloperService picDeveloperService;

    //Getting data PIC Developer with search and pagination
    @GetMapping()
    public ResponseEntity<?> searchPICDeveloper(@ModelAttribute PICDeveloperDTO searchDTO,
                                                @RequestParam("page")String page,
                                                @RequestParam("size")String size

    ) {
        log.info("page: " + size);
        log.info(("size: " + page));
        try {
            PaginationUtil<PICDeveloperEntity, PICDeveloperDTO> result = picDeveloperService.getAllPICDeveloperByPagination(Integer.parseInt(page), Integer.parseInt(size));
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, "Success retrieved data pic developer!", result), HttpStatus.OK);
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
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
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    //Creating data PIC Developer
    @PostMapping()
    public ResponseEntity<?> createPICDeveloper(
           @RequestBody @Valid PICDeveloperDTO request
    ) {
        try {
            PICDeveloperEntity result = picDeveloperService.createPICDeveloper(request);
            ApiResponse<PICDeveloperEntity> response = new ApiResponse<>(HttpStatus.CREATED, "Success created data pic developer!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    //Updating data PIC Developer By UUID
    @PutMapping("/{uuid}")
    public ResponseEntity<?> updatePICDeveloper(
            @PathVariable("uuid") UUID uuid,
            @Valid @RequestBody PICDeveloperDTO request
    ) {
        try {
            PICDeveloperEntity result = picDeveloperService.updatePICDeveloper(uuid, request);
            ApiResponse<PICDeveloperEntity> response = new ApiResponse<>(HttpStatus.ACCEPTED, "Success updated data pic developer!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
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
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }
}
