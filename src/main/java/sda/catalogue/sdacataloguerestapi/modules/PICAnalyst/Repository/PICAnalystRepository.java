package sda.catalogue.sdacataloguerestapi.modules.PICAnalyst.Repository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sda.catalogue.sdacataloguerestapi.modules.PICAnalyst.Entities.PICAnalystEntity;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Entities.PICDeveloperEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PICAnalystRepository extends JpaRepository<PICAnalystEntity, Long>, JpaSpecificationExecutor<PICAnalystEntity> {
    //Getting data PIC Analyst with search and pagination
    @Query("SELECT w FROM PICAnalystEntity w " +
            "WHERE LOWER(w.personalNumber) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.personalName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "ORDER BY w.updatedAt DESC")
    List<PICAnalystEntity> findBySearchTerm(String searchTerm, Pageable pageable);

    //Counting data PIC Analyst with search
    @Query("SELECT COUNT(w) FROM PICAnalystEntity w " +
            "WHERE " +
            "LOWER(w.personalNumber) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(w.personalName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) ")
    long countBySearchTerm(String searchTerm);

    //Getting data PIC Analyst by UUID
    Optional<PICAnalystEntity> findByUuid(UUID uuid);

    //Updating data PIC Analyst by UUID
    @Modifying
    @Transactional
    @Query("UPDATE PICAnalystEntity w SET " +
            "w.personalNumber = :personalNumber, " +
            "w.personalName = :personalName " +
            "WHERE w.uuid = :uuid")
    int findByUuidAndUpdate(
            UUID uuid,
            String personalNumber,
            String personalName
    );

    //Deleting data PIC Analyst by UUID
    @Modifying
    @Transactional
    @Query("DELETE FROM PICAnalystEntity w WHERE w.uuid = :uuid")
    int findByUuidAndDelete(UUID uuid);

    List<PICAnalystEntity> findByPersonalNameIsIn(Collection<String> name);

    List<PICAnalystEntity> findByIdPicAnalystIsIn(Collection<Long> id);

    default boolean existsByPICAnalyst(String picDeveloper) {
        return false;
    }
}
