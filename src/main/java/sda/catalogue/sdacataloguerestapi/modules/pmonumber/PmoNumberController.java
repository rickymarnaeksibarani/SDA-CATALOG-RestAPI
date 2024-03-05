package sda.catalogue.sdacataloguerestapi.modules.pmonumber;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.ApiResponse;
import sda.catalogue.sdacataloguerestapi.core.Exception.CustomRequestException;
import sda.catalogue.sdacataloguerestapi.core.utils.PaginationUtil;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Dto.FrontEndDTO;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Dto.FrontEndRequestDTO;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Entities.FrontEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.pmonumber.dto.PmoNumberDTO;
import sda.catalogue.sdacataloguerestapi.modules.pmonumber.dto.PmoNumberRequestDTO;
import sda.catalogue.sdacataloguerestapi.modules.pmonumber.entity.PmoNumberEntity;

import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/pmo-number")
@CrossOrigin(origins = "${spring.frontend}")
public class PmoNumberController {
    @Autowired
    private PmoNumberService pmonumberservice;

    @GetMapping()
    public ResponseEntity<?> searchPMO(PmoNumberRequestDTO searchDTO) {

        try {
            PaginationUtil<PmoNumberEntity, PmoNumberEntity> result = pmonumberservice.getAllPmoNumberByPagination(searchDTO);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, "Success retrieved data pmo number!", result), HttpStatus.OK);
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getPmoNumberByUuid(
            @PathVariable("uuid") UUID uuid
    ) {
        try {
            PmoNumberEntity result = pmonumberservice.getPmoNumberByUuid(uuid);
            ApiResponse<PmoNumberEntity> response = new ApiResponse<>(HttpStatus.OK, "Success retrieved data pmo number!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @PostMapping()
    public ResponseEntity<?> createPMO(
            @Valid @RequestBody PmoNumberDTO request
    ) {
        try {
            PmoNumberEntity result = pmonumberservice.createPMO(request);
            ApiResponse<PmoNumberEntity> response = new ApiResponse<>(HttpStatus.CREATED, "Success create data pmo number!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> updatePMO(
            @PathVariable("uuid") UUID uuid,
            @Valid @RequestBody PmoNumberDTO request
    ) {
        try {
            PmoNumberEntity result = pmonumberservice.updatePMO(uuid, request);
            ApiResponse<PmoNumberEntity> response = new ApiResponse<>(HttpStatus.ACCEPTED, "Success update data PMO Number!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deletePMO(
            @PathVariable("uuid") UUID uuid
    ) {
        try {
            PmoNumberEntity result = pmonumberservice.deletePMO(uuid);
            ApiResponse<PmoNumberEntity> response = new ApiResponse<>(HttpStatus.OK, "Success delete data PMO Number!", result);
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }
}
