package sda.catalogue.sdacataloguerestapi.modules.BackEnd.Services;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.PaginateResponse;
import sda.catalogue.sdacataloguerestapi.core.Exception.CustomRequestException;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Dto.BackEndDTO;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Entities.BackEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Repositories.BackEndRepository;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class BackEndService {
    @Autowired
    private BackEndRepository backendRepository;

//    public Object test() {
//        Pageable page = Pageable.ofSize(1);
//        Page<BackEndEntity> paginationBackendEntity = this.backendRepository.findAll(page);
//
//        log.info(paginationBackendEntity);
//    }
    public PaginateResponse<List<BackEndEntity>> searchBackEnd(String searchTerm, long page, long size) {
        Pageable pageable = PageRequest.of((int) (page - 1), (int) size);
        List<BackEndEntity> result = backendRepository.findBySearchTerm(searchTerm, pageable);
        long total = backendRepository.countBySearchTerm(searchTerm);
        PaginateResponse.Page pageInfo = new PaginateResponse.Page(size, total, page);
        return new PaginateResponse<>(result, pageInfo);
    }

    public BackEndEntity getBackEndByUuid(UUID uuid) {
        return backendRepository.findByUuid(uuid);
    }

    public BackEndEntity createBackend(BackEndDTO request) {
        BackEndEntity data = new BackEndEntity();
        data.setBackEnd(request.getBackEnd());
        return backendRepository.save(data);
    }

    @Transactional
    public BackEndEntity updateBackend(UUID uuid, BackEndDTO request) {
        int result = backendRepository.findByUuidAndUpdate(uuid, request.getBackEnd());
        if (result > 0) {
            return backendRepository.findByUuid(uuid);
        } else {
            throw new CustomRequestException("UUID " + uuid + " not found", HttpStatus.NOT_FOUND);
        }
    }

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
