package sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.PaginateResponse;
import sda.catalogue.sdacataloguerestapi.core.Exception.CustomRequestException;
import sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Dto.SDAHostingDTO;
import sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Entities.SDAHostingEntity;
import sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Repositories.SDAHostingRepository;

import java.util.List;
import java.util.UUID;

@Service
public class SDAHostingService {

    @Autowired
    private SDAHostingRepository sdaHostingRepository;


    @Transactional
    public PaginateResponse<List<SDAHostingEntity>> searchAndPaginate(String searchTerm, long page, long size) {
        Pageable pageable = PageRequest.of((int) (page - 1), (int) size);
        List<SDAHostingEntity> result = sdaHostingRepository.findBySearchTerm(searchTerm, pageable);
        long total = sdaHostingRepository.countBySearchTerm(searchTerm);
        PaginateResponse.Page pageInfo = new PaginateResponse.Page(size, total, page);
        return new PaginateResponse<>(result, pageInfo);
    }


    public SDAHostingEntity getSDAHostingByUuid(UUID uuid) {
        SDAHostingEntity result = sdaHostingRepository.findByUuid(uuid);
        if (result == null) {
            throw new CustomRequestException("UUID " + uuid + " not found", HttpStatus.NOT_FOUND);
        }
        return result;
    }

    public SDAHostingEntity createSDAHosting(SDAHostingDTO request) {
        SDAHostingEntity data = new SDAHostingEntity();
        data.setSdaHosting(request.getSdaHosting());
        return sdaHostingRepository.save(data);
    }

    @Transactional
    public SDAHostingEntity updateSDAHosting(UUID uuid, SDAHostingDTO request) {
        int result = sdaHostingRepository.findByUuidAndUpdate(uuid, request.getSdaHosting());
        if (result > 0) {
            return sdaHostingRepository.findByUuid(uuid);
        } else {
            throw new CustomRequestException("UUID " + uuid + " not found", HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public SDAHostingEntity deleteSDAHosting(UUID uuid) {
        SDAHostingEntity findData = sdaHostingRepository.findByUuid(uuid);
        int result = sdaHostingRepository.findByUuidAndDelete(uuid);
        if (result > 0) {
            return findData;
        } else {
            throw new CustomRequestException("UUID " + uuid + " not found", HttpStatus.NOT_FOUND);
        }
    }
}
