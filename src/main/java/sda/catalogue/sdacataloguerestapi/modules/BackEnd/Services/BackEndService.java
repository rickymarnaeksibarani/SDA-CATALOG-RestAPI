package sda.catalogue.sdacataloguerestapi.modules.BackEnd.Services;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
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
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Dto.BackEndRequestDTO;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Entities.BackEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Repositories.BackEndRepository;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Dto.FrontEndDTO;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Entities.FrontEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Dto.PICDeveloperDTO;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Entities.PICDeveloperEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Dto.WebAppRequestDto;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.WebAppEntity;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class BackEndService {
    @Autowired
    private BackEndRepository backendRepository;

    //Getting data Backend with pagination
    public PaginationUtil<BackEndEntity, BackEndEntity> getAllBackendByPagination(BackEndRequestDTO searchRequest) {
        Pageable paging = PageRequest.of(searchRequest.getPage() - 1, searchRequest.getSize());
        Specification<BackEndEntity> specs = Specification.where(null);
        Page<BackEndEntity> pagedResult = backendRepository.findAll(specs, paging);
        return new PaginationUtil<>(pagedResult, BackEndEntity.class);
    }

    //Getting data Back end by UUID
    public BackEndEntity getBackEndByUuid(UUID uuid) {
        BackEndEntity result = backendRepository.findByUuid(uuid);
        if (result == null) {
            throw new CustomRequestException("UUID " + uuid + " not found", HttpStatus.NOT_FOUND);
        }
        return result;
    }
    //Creating data Back end
    public BackEndEntity createBackend(BackEndDTO request) {
        BackEndEntity data = new BackEndEntity();
        data.setBackEnd(request.getBackEnd());
        return backendRepository.save(data);
    }

    //Updating data Back end by UUID
    @Transactional
    public BackEndEntity updateBackend(UUID uuid, BackEndDTO request) {
        int result = backendRepository.findByUuidAndUpdate(
                uuid,
                request.getBackEnd()
        );
        BackEndEntity findData = backendRepository.findByUuid(uuid);
        if (result > 0) {
            return backendRepository.findByUuid(uuid);
        } else {
            throw new CustomRequestException("UUID " + uuid + " not found", HttpStatus.NOT_FOUND);
        }
    }

    //Deleting data Back end by UUID
    @Transactional
    public BackEndEntity deleteBackend(UUID uuid) {
        BackEndEntity findData = backendRepository.findByUuid(uuid);
        int result = backendRepository.findByUuidAndDelete(uuid);
        if (result > 0) {
            return findData;
        } else {
            throw new CustomRequestException("UUID " + uuid + " not found", HttpStatus.NOT_FOUND);
        }
    }
}
