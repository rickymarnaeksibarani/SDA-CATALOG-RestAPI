package sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Services;

import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sda.catalogue.sdacataloguerestapi.core.CustomResponse.PaginateResponse;
import sda.catalogue.sdacataloguerestapi.core.Exception.CustomRequestException;
import sda.catalogue.sdacataloguerestapi.core.utils.PaginationUtil;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Dto.BackEndDTO;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Entities.BackEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Dto.PICDeveloperDTO;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Dto.PICDeveloperRequestDTO;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Entities.PICDeveloperEntity;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Repositories.PICDeveloperRepository;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Dto.WebAppRequestDto;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.WebAppEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PICDeveloperService {
    @Autowired
    private PICDeveloperRepository pICDeveloperRepository;


    //Getting data PIC Developer with search and pagination
    public PaginationUtil<PICDeveloperEntity, PICDeveloperEntity> getAllPICDeveloperByPagination(PICDeveloperRequestDTO searchRequest) {
        Specification<PICDeveloperEntity> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (searchRequest.getSearchTerm() != null) {
                predicates.add(
                        builder.or(
                            builder.like(builder.upper(root.get("personalName")), "%" + searchRequest.getSearchTerm().toUpperCase() + "%"),
                            builder.like(builder.upper(root.get("personalNumber")), "%" + searchRequest.getSearchTerm().toUpperCase() + "%")
                        )
                );
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        Pageable paging = PageRequest.of(searchRequest.getPage() - 1, searchRequest.getSize());
        Page<PICDeveloperEntity> pagedResult = pICDeveloperRepository.findAll(specification, paging);
        return new PaginationUtil<>(pagedResult, PICDeveloperEntity.class);
    }

    //Getting data PIC Developer by ID
    public PICDeveloperEntity getPICDeveloperByUUID(Long id_pic_developer) {
        PICDeveloperEntity result = pICDeveloperRepository.findById(id_pic_developer).orElse(null);
        if (result == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"ID" + id_pic_developer + " not found");
        }
        return result;
    }

    //Creating data PIC Developer
    public PICDeveloperEntity createPICDeveloper(PICDeveloperDTO request) {
        boolean existsByPicDeveloper = pICDeveloperRepository.existsByPICDeveloper(request.getPersonalName());
        if (existsByPicDeveloper){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "PIC Developer already exists");
        }

        PICDeveloperEntity data = new PICDeveloperEntity();
        data.setPersonalName(request.getPersonalName());
        data.setPersonalNumber(request.getPersonalNumber());
        return pICDeveloperRepository.save(data);
    }

    //Updating data PIC Developer By UUID
    @Transactional
    public PICDeveloperEntity updatePICDeveloper(UUID uuid, PICDeveloperDTO request) {
        PICDeveloperEntity picDeveloper = pICDeveloperRepository.findByUuid(uuid)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.CONFLICT, "PIC Developer does not exists"));
        picDeveloper.setPersonalNumber(request.getPersonalNumber());
        picDeveloper.setPersonalName(request.getPersonalName());
        return pICDeveloperRepository.save(picDeveloper);
    }


    //Deleting data PIC Developer By UUID
    @Transactional
    public void deletePICDeveloper(UUID uuid){
        PICDeveloperEntity findData = pICDeveloperRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "PIC Developer does not exist"));
        pICDeveloperRepository.delete(findData);
    }
}