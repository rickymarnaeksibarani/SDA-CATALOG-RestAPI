package sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Services;

import jakarta.persistence.criteria.Predicate;
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
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Dto.MappingFunctionRequestDTO;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Entities.DinasEntity;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Entities.MappingFunctionEntity;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Repositories.DinasRepository;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Repositories.MappingFunctionRepository;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Dto.PICDeveloperDTO;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Entities.PICDeveloperEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Dto.WebAppRequestDto;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.WebAppEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MappingFunctionService {
    @Autowired
    private MappingFunctionRepository mappingFunctionRepository;

    @Autowired
    private DinasRepository dinasRepository;

    //Getting data Mapping Function with search and pagination
    public PaginationUtil<MappingFunctionEntity, MappingFunctionEntity> getAllMappingFunctionByPagination(MappingFunctionRequestDTO searchRequest) {
        Specification<MappingFunctionEntity> spec =  (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (searchRequest.getSearchTerm() != null) {
                predicates.add(
                        builder.like(builder.upper(root.get("mappingFunction")), "%" + searchRequest.getSearchTerm().toUpperCase() + "%")
                );
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        Pageable paging = PageRequest.of(searchRequest.getPage() - 1, searchRequest.getSize());
        Page<MappingFunctionEntity> pagedResult = mappingFunctionRepository.findAll(spec, paging);
        return new PaginationUtil<>(pagedResult, MappingFunctionEntity.class);
    }



    //Getting data Mapping Function with UUID
    public MappingFunctionEntity getMappingFunctionByUuid(UUID uuid) {
        MappingFunctionEntity result = mappingFunctionRepository.findByUuid(uuid);
        if (result == null) {
            throw new CustomRequestException("UUID " + uuid + " not found", HttpStatus.NOT_FOUND);
        }
        return result;
    }

    //Creating data Mapping Function
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
