package sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Services;

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
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Dto.DinasDTO;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Dto.MappingFunctionDTO;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Entities.DinasEntity;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Entities.MappingFunctionEntity;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Repositories.DinasRepository;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Repositories.MappingFunctionRepository;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Dto.PICDeveloperDTO;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Entities.PICDeveloperEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class MappingFunctionService {
    @Autowired
    private MappingFunctionRepository mappingFunctionRepository;

    @Autowired
    private DinasRepository dinasRepository;

    //Getting data PIC Developer with search and pagination
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

    @Transactional
    public MappingFunctionEntity createMappingFunction(MappingFunctionDTO request) {
        MappingFunctionEntity data = new MappingFunctionEntity();
        data.setMappingFunction(request.getMappingFunction());
        MappingFunctionEntity mappingFunctionProcess = mappingFunctionRepository.save(data);

        if (request.getDinasList() != null) {
            List<DinasEntity> dinasList = new ArrayList<>();
            for (DinasDTO dataDinas : request.getDinasList()) {
                if (dataDinas.getDinas() != null) {
                    DinasEntity dinasItem = new DinasEntity();
                    dinasItem.setMappingFunctionEntity(mappingFunctionProcess);
                    dinasItem.setDinas(dataDinas.getDinas());
                    dinasList.add(dinasItem);
                }
            }
            dinasRepository.saveAll(dinasList);
            data.setDinasEntityList(dinasList);
        } else {
            data.setDinasEntityList(null);
        }
        return mappingFunctionProcess;
    }

    @Transactional
    public MappingFunctionEntity updateMappingFunctionByUuid(UUID uuid, MappingFunctionDTO request) {
        int mappingFunctionProcess = mappingFunctionRepository.findByUuidAndUpdate(uuid, request.getMappingFunction());
        MappingFunctionEntity findData = mappingFunctionRepository.findByUuid(uuid);
        dinasRepository.deleteAllByMappingFunctionEntity(findData);

        List<DinasEntity> dinasList = new ArrayList<>();
        for (DinasDTO dataDinas : request.getDinasList()) {
            DinasEntity dinasItem = new DinasEntity();
            dinasItem.setMappingFunctionEntity(findData);
            dinasItem.setDinas(dataDinas.getDinas());
            dinasList.add(dinasItem);
        }
        dinasRepository.saveAll(dinasList);
        if (mappingFunctionProcess > 0) {
            return mappingFunctionRepository.findByUuid(uuid);
        } else {
            throw new CustomRequestException("UUID " + uuid + " not found", HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public MappingFunctionEntity deleteMappingFunctionByUuid(UUID uuid) {
        MappingFunctionEntity findData = mappingFunctionRepository.findByUuid(uuid);
        int result = mappingFunctionRepository.deleteByUuid(uuid);
        if (result > 0) {
            return findData;
        } else {
            throw new CustomRequestException("UUID " + uuid + " not found", HttpStatus.NOT_FOUND);
        }
    }
}
