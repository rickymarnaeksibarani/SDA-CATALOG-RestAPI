package sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Controllers;

import javax.validation.Valid;
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
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Dto.MappingFunctionRequestDTO;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Entities.MappingFunctionEntity;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Services.MappingFunctionService;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Dto.PICDeveloperDTO;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Entities.PICDeveloperEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Dto.WebAppRequestDto;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.WebAppEntity;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/mapping-function")
@CrossOrigin(origins = "${spring.frontend}")
public class MappingFunctionController {
    @Autowired
    private MappingFunctionService mappingFunctionService;

    //Getting data Mapping Function with search and pagination
    @GetMapping()
    public ResponseEntity<?> searchMappingFunction(MappingFunctionRequestDTO searchDTO) {

        try {
            PaginationUtil<MappingFunctionEntity, MappingFunctionEntity> result = mappingFunctionService.getAllMappingFunctionByPagination(searchDTO);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, "Success retrieved data Mapping function!", result), HttpStatus.OK);
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    //Getting data Mapping Function with UUID
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

    //Creating data Mapping Function
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

    //Update data Mapping Function by UUID
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

    //Delete data Mapping Function by UUID
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
