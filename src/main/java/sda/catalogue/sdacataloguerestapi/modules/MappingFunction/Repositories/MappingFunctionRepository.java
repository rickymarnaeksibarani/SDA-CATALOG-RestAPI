package sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Entities.MappingFunctionEntity;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface MappingFunctionRepository extends JpaRepository<MappingFunctionEntity, Long>, JpaSpecificationExecutor<MappingFunctionEntity> {
    @Query("SELECT w FROM MappingFunctionEntity w " +
            "WHERE LOWER(w.mappingFunction) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "ORDER BY w.updatedAt DESC")
    List<MappingFunctionEntity> findBySearchTerm(String searchTerm, Pageable pageable);


    @Query("SELECT COUNT(w) FROM MappingFunctionEntity w " +
            "WHERE LOWER(w.mappingFunction) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    long countBySearchTerm(String searchTerm);

    MappingFunctionEntity findByUuid(UUID uuid);

    @Modifying
    @Transactional
    @Query("UPDATE MappingFunctionEntity w SET " +
            "w.mappingFunction = :mappingFunction " +
            "WHERE w.uuid = :uuid")
    int findByUuidAndUpdate(UUID uuid, String mappingFunction);


    @Transactional
    int deleteByUuid(UUID uuid);

    List<MappingFunctionEntity> findByMappingFunctionIsIn(Collection<String> mappingFunction);

    List<MappingFunctionEntity> findByIdMappingFunctionIsIn(Collection<Long> id);
}
