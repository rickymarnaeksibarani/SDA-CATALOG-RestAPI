package sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Entities.TypeDatabaseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TypeDatabaseRepository extends JpaRepository<TypeDatabaseEntity, Long>, JpaSpecificationExecutor<TypeDatabaseEntity> {

    //Getting data Type Database with search and pagination
    @Query("SELECT w FROM TypeDatabaseEntity w " +
            "WHERE LOWER(w.typeDatabase) LIKE LOWER(CONCAT('%', :searchTerm,'%')) " +
            "ORDER BY w.updatedAt DESC")
    List<TypeDatabaseEntity> findBySearchTerm(String searchTerm, Pageable pageable);

    //Counting data Type Database with search
    @Query("SELECT COUNT(w) FROM TypeDatabaseEntity w " +
            "WHERE LOWER(w.typeDatabase) LIKE LOWER(CONCAT('%', :searchTerm,'%'))")
    long countBySearchTerm(String searchTerm);

    //Getting data Type Database by UUID
    Optional<TypeDatabaseEntity> findByUuid(UUID uuid);

    //Updating data Type Database by UUID
    @Modifying
    @Transactional
    @Query("UPDATE TypeDatabaseEntity w SET " +
            "w.typeDatabase = :typeDatabase " +
            "WHERE w.uuid = :uuid")
    int findByUuidAndUpdate(UUID uuid, String typeDatabase);

    //Deleting data Type Database by UUID
    @Modifying
    @Transactional
    @Query("DELETE FROM TypeDatabaseEntity w WHERE w.uuid = :uuid")
    int findByUuidAndDelete(UUID uuid);

    boolean existsByTypeDatabase(String dbName);
}
