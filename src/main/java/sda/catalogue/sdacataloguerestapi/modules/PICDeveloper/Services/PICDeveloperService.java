package sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.PaginateResponse;
import sda.catalogue.sdacataloguerestapi.core.Exception.CustomRequestException;
import sda.catalogue.sdacataloguerestapi.core.utils.PaginationUtil;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Dto.BackEndDTO;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Entities.BackEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Dto.PICDeveloperDTO;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Entities.PICDeveloperEntity;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Repositories.PICDeveloperRepository;

import java.util.List;
import java.util.UUID;

@Service
public class PICDeveloperService {
    @Autowired
    private PICDeveloperRepository pICDeveloperRepository;


    //Getting data PIC Developer with search and pagination
    public PaginationUtil<PICDeveloperEntity, PICDeveloperDTO> getAllPICDeveloperByPagination() {
        Pageable paging = PageRequest.of(0, 20);
        Specification<PICDeveloperEntity> specs = Specification.where(null);
        Page<PICDeveloperEntity> pagedResult = pICDeveloperRepository.findAll(specs, paging);
        return new PaginationUtil<>(pagedResult, PICDeveloperDTO.class);
    }

    //Getting data PIC Developer by UUID
    public PICDeveloperEntity getPICDeveloperByUUID(UUID uuid) {
        PICDeveloperEntity result = pICDeveloperRepository.findByUuid(uuid);
        if (result == null) {
            throw new CustomRequestException("UUID " + uuid + " not found", HttpStatus.NOT_FOUND);
        }
        return result;
    }

    //Creating data PIC Developer
    public PICDeveloperEntity createPICDeveloper(PICDeveloperDTO request) {
        PICDeveloperEntity data = new PICDeveloperEntity();
        data.setPersonalName(request.getPersonalName());
        data.setPersonalNumber(request.getPersonalNumber());
        return pICDeveloperRepository.save(data);
    }

    //Updating data PIC Developer By UUID
    @Transactional
    public PICDeveloperEntity updatePICDeveloper(UUID uuid, PICDeveloperDTO request) {
        int result = pICDeveloperRepository.findByUuidAndUpdate(
                uuid,
                request.getPersonalName(),
                request.getPersonalNumber()
        );
        if (result > 0) {
            return pICDeveloperRepository.findByUuid(uuid);
        } else {
            throw new CustomRequestException("UUID " + uuid + " not found", HttpStatus.NOT_FOUND);
        }
    }


    //Deleting data PIC Developer By UUID
    @Transactional
    public PICDeveloperEntity deletePICDeveloperByUuid(UUID uuid) {
        PICDeveloperEntity findData = pICDeveloperRepository.findByUuid(uuid);
        int result = pICDeveloperRepository.findByUuidAndDelete(uuid);
        if (result > 0) {
            return findData;
        } else {
            throw new CustomRequestException("UUID " + uuid + " not found", HttpStatus.NOT_FOUND);
        }
    }

}