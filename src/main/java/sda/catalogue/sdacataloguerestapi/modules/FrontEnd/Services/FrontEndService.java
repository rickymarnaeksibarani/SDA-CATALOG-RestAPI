package sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Services;

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
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Dto.FrontEndDTO;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Dto.FrontEndRequestDTO;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Entities.FrontEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Repositories.FrontEndRepository;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Dto.WebAppRequestDto;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.WebAppEntity;

import java.util.List;
import java.util.UUID;

@Service
public class FrontEndService {
    @Autowired
    private FrontEndRepository frontEndRepository;

    //Getting data Front end with pagination
    public PaginationUtil<FrontEndEntity, FrontEndEntity> getAllFrontendByPagination(FrontEndRequestDTO searchRequest) {
        Pageable paging = PageRequest.of(searchRequest.getPage() - 1, searchRequest.getSize());
        Specification<FrontEndEntity> specs = Specification.where(null);
        Page<FrontEndEntity> pagedResult = frontEndRepository.findAll(specs, paging);
        return new PaginationUtil<>(pagedResult, FrontEndEntity.class);
    }

    //Get data Front end by UUID
    public FrontEndEntity getFrontEndByUuid(UUID uuid) {
        FrontEndEntity result = frontEndRepository.findByUuid(uuid);
        if (result == null) {
            throw new CustomRequestException("UUID" + uuid + " not found", HttpStatus.NOT_FOUND);
        }
        return result;
    }

    //Creating data Front end
    public FrontEndEntity createFrontEnd(FrontEndDTO request) {
        FrontEndEntity data = new FrontEndEntity();
        data.setFrontEnd(request.getFrontEnd());
        return frontEndRepository.save(data);
    }


    //Updating data Front end by UUID
    @Transactional
    public FrontEndEntity updateFrontEnd(UUID uuid, FrontEndDTO request) {
        int result = frontEndRepository.findByUuidAndUpdate(uuid, request.getFrontEnd());
        FrontEndEntity findData = frontEndRepository.findByUuid(uuid);
        if (result > 0) {
            return findData;
        } else {
            throw new CustomRequestException("UUID " + uuid + " not found", HttpStatus.NOT_FOUND);
        }
    }

    //Deleting data Front end by UUID
    @Transactional
    public FrontEndEntity deleteFrontEnd(UUID uuid) {
        FrontEndEntity findData = frontEndRepository.findByUuid(uuid);
        int result = frontEndRepository.findByUuidAndDelete(uuid);
        if (result > 0) {
            return findData;
        } else {
            throw new CustomRequestException("UUID " + uuid + " not found", HttpStatus.NOT_FOUND);
        }
    }
}