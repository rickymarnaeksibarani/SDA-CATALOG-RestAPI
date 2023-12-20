package sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.PaginateResponse;
import sda.catalogue.sdacataloguerestapi.core.Exception.CustomRequestException;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Dto.MappingFunctionDTO;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Entities.MappingFunctionEntity;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Repositories.MappingFunctionRepository;

import java.util.List;
import java.util.UUID;

@Service
public class MappingFunctionService {
    @Autowired
    private MappingFunctionRepository mappingFunctionRepository;

    @Transactional
    public PaginateResponse<List<MappingFunctionEntity>> searchAndPaginate(String searchTerm, long page, long size) {
        Pageable pageable = PageRequest.of((int) (page - 1), (int) size);
        List<MappingFunctionEntity> result = mappingFunctionRepository.findBySearchTerm(searchTerm, pageable);
        long total = mappingFunctionRepository.countBySearchTerm(searchTerm);
        PaginateResponse.Page pageInfo = new PaginateResponse.Page(size, total, page);
        return new PaginateResponse<>(result, pageInfo);
    }

    public MappingFunctionEntity getMappingFunctionByUuid(UUID uuid) {
        MappingFunctionEntity result = mappingFunctionRepository.findByUuid(uuid);
        if (result == null) {
            throw new CustomRequestException("UUID " + uuid + " not found", HttpStatus.NOT_FOUND);
        }
        return result;
    }

    public MappingFunctionEntity createMappingFunction(MappingFunctionDTO request) {
        MappingFunctionEntity data = new MappingFunctionEntity();
        data.setMappingFunction(request.getMappingFunction());
        return mappingFunctionRepository.save(data);
    }

//    public MappingFunctionEntity updateMappingFunctionByUuid(UUID uuid, MappingFunctionDTO request) {
//        return mappingFunctionRepository.findByUuidAndUpdate(uuid, request.getMappingFunction())
//    }
}
