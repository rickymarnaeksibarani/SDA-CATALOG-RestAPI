package sda.catalogue.sdacataloguerestapi.modules.PICAnalyst;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.ApiResponse;
import sda.catalogue.sdacataloguerestapi.core.Exception.CustomRequestException;
import sda.catalogue.sdacataloguerestapi.core.utils.PaginationUtil;
import sda.catalogue.sdacataloguerestapi.modules.PICAnalyst.Dto.PICAnalystDTO;
import sda.catalogue.sdacataloguerestapi.modules.PICAnalyst.Dto.PICAnalystRequestDTO;
import sda.catalogue.sdacataloguerestapi.modules.PICAnalyst.Entities.PICAnalystEntity;

import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/pic-analyst")
public class PICAnalystController {
    @Autowired
    private PICAnalystService picAnalystService;

    //Getting data PIC Analyst with search and pagination
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchPIC(PICAnalystRequestDTO searchDTO) {

        try {
            PaginationUtil<PICAnalystEntity, PICAnalystEntity> result = picAnalystService.getAllPICAnalystByPagination(searchDTO);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, "Success retrieved data pic developer!", result), HttpStatus.OK);
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    //Getting data PIC Analyst by UUID
    @GetMapping(value = "/{id_pic_analyst}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPicAnalystById(
            @PathVariable("id_pic_analyst") Long id_pic_analyst
    ) {
        try {
            PICAnalystEntity result = picAnalystService.getPICAnalystByUUID(id_pic_analyst);
            ApiResponse<PICAnalystEntity> response = new ApiResponse<>(HttpStatus.OK, "Success retrieved data pic analyst!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    //Creating data PIC Analyst
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createPICAnalyst(
            @RequestBody @Valid PICAnalystDTO request
    ) {
        try {
            PICAnalystEntity result = picAnalystService.createPICAnalyst(request);
            ApiResponse<PICAnalystEntity> response = new ApiResponse<>(HttpStatus.CREATED, "Success created data pic analyst!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    //Updating data PIC Analyst By UUID
    @PutMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updatePICAnalyst(
            @PathVariable("uuid") UUID uuid,
            @Valid @RequestBody PICAnalystDTO request
    ) {
        try {
            PICAnalystEntity result = picAnalystService.updatePICAnalyst(uuid, request);
            ApiResponse<PICAnalystEntity> response = new ApiResponse<>(HttpStatus.ACCEPTED, "Success updated data pic analyst!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    //Deleting data PIC Analyst By UUID
    @DeleteMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deletePICAnalystByUuid(
            @PathVariable("uuid") UUID uuid
    ) {
        try {
            picAnalystService.deletePICAnalyst(uuid);
            ApiResponse<String> response = new ApiResponse<>(HttpStatus.OK, "Success delete data pic analyst!", "DELETED");
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }
}
