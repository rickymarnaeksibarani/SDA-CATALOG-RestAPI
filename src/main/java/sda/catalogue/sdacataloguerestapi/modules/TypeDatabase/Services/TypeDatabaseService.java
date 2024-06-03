package sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Services;

import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import sda.catalogue.sdacataloguerestapi.core.utils.PaginationUtil;
import sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Dto.TypeDatabaseDTO;
import sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Dto.TypeDatabaseRequestDTO;
import sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Entities.TypeDatabaseEntity;
import sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Repositories.TypeDatabaseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TypeDatabaseService {
    @Autowired
    private TypeDatabaseRepository typeDatabaseRepository;

    //Getting data PIC Developer with search and pagination
    public PaginationUtil<TypeDatabaseEntity, TypeDatabaseEntity> getAllTypeDatabaseByPagination(TypeDatabaseRequestDTO searchRequest) {
        Specification<TypeDatabaseEntity> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (searchRequest.getSearchTerm() != null) {
              predicates.add(
                      builder.like(builder.upper(root.get("typeDatabase")), "%" + searchRequest.getSearchTerm().toUpperCase() + "%")
              );
            };

            if (searchRequest.getStatus() != null && !searchRequest.getStatus().isEmpty()) {
                predicates.add(
                        builder.in(root.get("dbStatus")).value(searchRequest.getStatus())
                );
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        Pageable paging = PageRequest.of(searchRequest.getPage()-1, searchRequest.getSize());
        Page<TypeDatabaseEntity> pagedResult = typeDatabaseRepository.findAll(specification, paging);
        return new PaginationUtil<>(pagedResult, TypeDatabaseEntity.class);
    }

    public TypeDatabaseEntity getTypeDatabaseByUuid(UUID uuid) {
        return typeDatabaseRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Type of Database not found"));
    }

    @Transactional
    public TypeDatabaseEntity createTypeDatabase(TypeDatabaseDTO request) {
        boolean isExists = typeDatabaseRepository.existsByTypeDatabase(request.getTypeDatabase());
        if (isExists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Type Database already exists");
        }

        TypeDatabaseEntity data = new TypeDatabaseEntity();
        data.setTypeDatabase(request.getTypeDatabase());
        data.setDbStatus(request.getDbStatus());
        return typeDatabaseRepository.save(data);
    }


    @Transactional
    public TypeDatabaseEntity updateTypeDatabase(UUID uuid, TypeDatabaseDTO request) {
        TypeDatabaseEntity typeOfDatabase = typeDatabaseRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Type of database not found"));

        typeOfDatabase.setTypeDatabase(request.getTypeDatabase());
        typeOfDatabase.setDbStatus(request.getDbStatus());
        return typeDatabaseRepository.save(typeOfDatabase);
    }

    @Transactional
    public void deleteTypeDatabase(UUID uuid) {
        TypeDatabaseEntity typeOfDatabase = typeDatabaseRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Type of database not found"));

        typeDatabaseRepository.delete(typeOfDatabase);
    }
}
