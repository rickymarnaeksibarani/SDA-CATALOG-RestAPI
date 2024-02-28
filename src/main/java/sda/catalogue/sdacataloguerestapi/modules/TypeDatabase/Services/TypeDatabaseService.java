package sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Services;

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
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Dto.PICDeveloperDTO;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Entities.PICDeveloperEntity;
import sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Dto.TypeDatabaseDTO;
import sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Dto.TypeDatabaseRequestDTO;
import sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Entities.TypeDatabaseEntity;
import sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Repositories.TypeDatabaseRepository;

import java.util.List;
import java.util.UUID;

@Service
public class TypeDatabaseService {
    @Autowired
    private TypeDatabaseRepository typeDatabaseRepository;

    //Getting data PIC Developer with search and pagination
    public PaginationUtil<TypeDatabaseEntity, TypeDatabaseEntity> getAllTypeDatabaseByPagination(TypeDatabaseRequestDTO searchRequest) {
        Pageable paging = PageRequest.of(searchRequest.getPage()-1, searchRequest.getSize());
        Specification<TypeDatabaseEntity> specs = Specification.where(null);
        Page<TypeDatabaseEntity> pagedResult = typeDatabaseRepository.findAll(specs, paging);
        return new PaginationUtil<>(pagedResult, TypeDatabaseEntity.class);
    }

    public TypeDatabaseEntity getTypeDatabaseByUuid(UUID uuid) {
        TypeDatabaseEntity result = typeDatabaseRepository.findByUuid(uuid);
        if (result == null) {
            throw new CustomRequestException("UUID " + uuid + " not found", HttpStatus.NOT_FOUND);
        }
        return result;
    }

    public TypeDatabaseEntity createTypeDatabase(TypeDatabaseDTO request) {
        TypeDatabaseEntity data = new TypeDatabaseEntity();
        data.setTypeDatabase(request.getTypeDatabase());
        return typeDatabaseRepository.save(data);
    }


    @Transactional
    public TypeDatabaseEntity updateTypeDatabase(UUID uuid, TypeDatabaseDTO request) {
        int result = typeDatabaseRepository.findByUuidAndUpdate(uuid, request.getTypeDatabase());
        if (result > 0) {
            return typeDatabaseRepository.findByUuid(uuid);
        } else {
            throw new CustomRequestException("UUID " + uuid + " not found", HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public TypeDatabaseEntity deleteTypeDatabase(UUID uuid) {
        TypeDatabaseEntity findData = typeDatabaseRepository.findByUuid(uuid);
        int result = typeDatabaseRepository.findByUuidAndDelete(uuid);
        if (result > 0) {
            return findData;
        } else {
            throw new CustomRequestException("UUID " + uuid + " not found", HttpStatus.NOT_FOUND);
        }
    }
}
