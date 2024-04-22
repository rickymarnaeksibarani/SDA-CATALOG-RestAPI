package sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Services;

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
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Dto.FrontEndDTO;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Dto.FrontEndRequestDTO;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Entities.FrontEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Repositories.FrontEndRepository;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Dto.WebAppRequestDto;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.WebAppEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FrontEndService {
    @Autowired
    private FrontEndRepository frontEndRepository;

    //Getting data Front end with pagination
    public PaginationUtil<FrontEndEntity, FrontEndEntity> getAllFrontendByPagination(FrontEndRequestDTO searchRequest) {
        Specification<FrontEndEntity> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (searchRequest.getSearchTerm() != null) {
                predicates.add(
                        builder.like(builder.upper(root.get("frontEnd")), "%" + searchRequest.getSearchTerm().toUpperCase() + "%")
                );
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        Pageable paging = PageRequest.of(searchRequest.getPage() - 1, searchRequest.getSize());
        Page<FrontEndEntity> pagedResult = frontEndRepository.findAll(specification, paging);
        return new PaginationUtil<>(pagedResult, FrontEndEntity.class);
    }

    //Get data Front end by ID
    public FrontEndEntity getFrontEndById(Long id_frontend) {
        FrontEndEntity result = frontEndRepository.findById(id_frontend).orElse(null);
        if (result == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID" + id_frontend + " not found");
        }
        return result;
    }

    //Creating data Front end
    public FrontEndEntity createFrontEnd(FrontEndDTO request) {
        boolean existsByFrontEnd = frontEndRepository.existsByFrontEnd(request.getFrontEnd());
        if (existsByFrontEnd) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Front End already exists");
        }

        FrontEndEntity data = new FrontEndEntity();
        data.setFrontEnd(request.getFrontEnd());
        data.setFeStatus(request.getFeStatus());
        return frontEndRepository.save(data);
    }


    //Updating data Front end by UUID
    @Transactional
    public FrontEndEntity updateFrontEnd(UUID uuid, FrontEndDTO request) {
        FrontEndEntity frontEnd = frontEndRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FrontEnd does not exist"));

        frontEnd.setFeStatus(request.getFeStatus());
        frontEnd.setFrontEnd(request.getFrontEnd());
        return frontEndRepository.save(frontEnd);
    }

    //Deleting data Front end by UUID
    @Transactional
    public void deleteFrontEnd(UUID uuid) {
        FrontEndEntity findData = frontEndRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FrontEnd does not exist"));

        frontEndRepository.delete(findData);
    }
}
