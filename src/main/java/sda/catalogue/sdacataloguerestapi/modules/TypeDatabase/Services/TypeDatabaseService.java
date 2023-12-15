package sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import sda.catalogue.sdacataloguerestapi.core.TangerangResponse.PaginateResponse;
import sda.catalogue.sdacataloguerestapi.core.TangerangValidation.TangerangRequestException;
import sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Dto.TypeDatabaseDTO;
import sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Entities.TypeDatabaseEntity;
import sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Repositories.TypeDatabaseRepository;

import java.util.List;
import java.util.UUID;

@Service
public class TypeDatabaseService {
    @Autowired
    private TypeDatabaseRepository typeDatabaseRepository;

    @Transactional
    public PaginateResponse<List<TypeDatabaseEntity>> searchAndPaginate(String searchTerm, long page, long size) {
        Pageable pageable = PageRequest.of((int) (page - 1), (int) size);
        List<TypeDatabaseEntity> result = typeDatabaseRepository.findBySearchTerm(searchTerm, pageable);
        long total = typeDatabaseRepository.countBySearchTerm(searchTerm);
        PaginateResponse.Page pageInfo = new PaginateResponse.Page(size, total, page);
        return new PaginateResponse<>(result, pageInfo);
    }

    public TypeDatabaseEntity getTypeDatabaseByUuid(UUID uuid) {
        TypeDatabaseEntity result = typeDatabaseRepository.findByUuid(uuid);
        if (result == null) {
            throw new TangerangRequestException("UUID " + uuid + " not found", HttpStatus.NOT_FOUND);
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
            throw new TangerangRequestException("UUID " + uuid + " not found", HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public TypeDatabaseEntity deleteTypeDatabase(UUID uuid) {
        TypeDatabaseEntity findData = typeDatabaseRepository.findByUuid(uuid);
        int result = typeDatabaseRepository.findByUuidAndDelete(uuid);
        if (result > 0) {
            return findData;
        } else {
            throw new TangerangRequestException("UUID " + uuid + " not found", HttpStatus.NOT_FOUND);
        }
    }
}
