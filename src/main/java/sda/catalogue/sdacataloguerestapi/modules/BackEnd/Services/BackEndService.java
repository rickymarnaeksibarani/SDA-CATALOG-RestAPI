package sda.catalogue.sdacataloguerestapi.modules.BackEnd.Services;

import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sda.catalogue.sdacataloguerestapi.core.Exception.CustomRequestException;
import sda.catalogue.sdacataloguerestapi.core.utils.PaginationUtil;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Dto.BackEndDTO;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Dto.BackEndRequestDTO;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Entities.BackEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Repositories.BackEndRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class BackEndService {
    @Autowired
    private BackEndRepository backendRepository;

    //Getting data Backend with pagination
    public PaginationUtil<BackEndEntity, BackEndEntity> getAllBackendByPagination(BackEndRequestDTO searchRequest) {
        Specification<BackEndEntity> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (searchRequest.getSearchTerm() != null) {
                predicates.add(
                        builder.like(builder.upper(root.get("backEnd")), "%" + searchRequest.getSearchTerm().toUpperCase() + "%")
                );
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        Pageable paging = PageRequest.of(searchRequest.getPage() - 1, searchRequest.getSize());
        Page<BackEndEntity> pagedResult = backendRepository.findAll(specification, paging);
        return new PaginationUtil<>(pagedResult, BackEndEntity.class);
    }

    //Getting data Back end by ID
    public BackEndEntity getBackEndById(Long id_backend) {
        return backendRepository.findById(id_backend)
                .orElseThrow(() -> new CustomRequestException("Backend not found", HttpStatus.NOT_FOUND));
    }

    @Transactional
    //Creating data Back end
    public BackEndEntity createBackend(BackEndDTO request) {
        boolean existsByBackEnd = backendRepository.existsByBackEnd(request.getBackEnd());

        if (existsByBackEnd) {
            throw new CustomRequestException("BackEnd already exists", HttpStatus.CONFLICT);
        }

        BackEndEntity data = new BackEndEntity();
        data.setBackEnd(request.getBackEnd());
        data.setBeStatus(request.getBeStatus());
        return backendRepository.save(data);
    }

    //Updating data Back end by UUID
    @Transactional
    public BackEndEntity updateBackend(UUID uuid, BackEndDTO request) {
        BackEndEntity backEndEntity = backendRepository.findByUuid(uuid).
                orElseThrow(() -> new CustomRequestException("Backend not found", HttpStatus.NOT_FOUND));

        backEndEntity.setBeStatus(request.getBeStatus());
        backEndEntity.setBackEnd(request.getBackEnd());
        return backendRepository.save(backEndEntity);
    }

    //Deleting data Back end by UUID
    @Transactional
    public void deleteBackend(UUID uuid) {
        BackEndEntity backEndEntity = backendRepository.findByUuid(uuid)
                .orElseThrow(() -> new CustomRequestException("ID " + uuid + " not found", HttpStatus.NOT_FOUND));

        backendRepository.delete(backEndEntity);
    }
}
