package sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Controllers;

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
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Dto.MappingFunctionDTO;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Entities.MappingFunctionEntity;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Services.MappingFunctionService;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Dto.PICDeveloperDTO;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Entities.PICDeveloperEntity;

import java.util.List;
import java.util.UUID;
@Slf4j
@RestController
@RequestMapping("/api/v1/mapping-function")
@CrossOrigin(origins = "${spring.frontend}")
public class MappingFunctionController {
    @Autowired
    private MappingFunctionService mappingFunctionService;

    //Getting data Mapping Function with search and pagination
    @GetMapping()
    public ResponseEntity<?> searchMappingFunction(@ModelAttribute MappingFunctionDTO searchDTO,
                                                   @RequestParam("page") String page,
                                                   @RequestParam("size") String size
    ) {
        log.info("page" +page);
        log.info("size"+size);

        try {
            PaginationUtil<MappingFunctionEntity, MappingFunctionDTO> result = mappingFunctionService.getAllMappingFunctionByPagination(Integer.parseInt(page), Integer.parseInt(size));
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, "Success retrieved data mapping function!", result), HttpStatus.OK);
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getMappingFunctionByUuid(
            @PathVariable("uuid") UUID uuid
    ) {
        try {
            MappingFunctionEntity result = mappingFunctionService.getMappingFunctionByUuid(uuid);
            ApiResponse<MappingFunctionEntity> response = new ApiResponse<>(HttpStatus.OK, "Success retrieved data mapping function!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @PostMapping()
    public ResponseEntity<?> createMappingFunction(
            @Valid @RequestBody MappingFunctionDTO request
    ) {
        try {
            MappingFunctionEntity result = mappingFunctionService.createMappingFunction(request);
            ApiResponse<MappingFunctionEntity> response = new ApiResponse<>(HttpStatus.CREATED, "Success created data mapping function!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> updateMappingFunction(
            @PathVariable("uuid") UUID uuid,
            @Valid @RequestBody MappingFunctionDTO request
    ) {
        try {
            MappingFunctionEntity result = mappingFunctionService.updateMappingFunctionByUuid(uuid, request);
            ApiResponse<MappingFunctionEntity> response = new ApiResponse<>(HttpStatus.ACCEPTED, "Success update data mapping function!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteMappingFunction(
            @PathVariable("uuid") UUID uuid
    ) {
        try {
            MappingFunctionEntity result = mappingFunctionService.deleteMappingFunctionByUuid(uuid);
            ApiResponse<MappingFunctionEntity> response = new ApiResponse<>(HttpStatus.OK, "Success delete data mapping function!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }
}
