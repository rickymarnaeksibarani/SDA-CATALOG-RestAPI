package sda.catalogue.sdacataloguerestapi.modules.WebApp.Repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.DatabaseEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.VersioningApplicationEntity;
import java.util.List;
import java.util.UUID;

@Repository
public interface VersioningApplicationRepository extends JpaRepository<VersioningApplicationEntity, Long> {
       //Getting data VersionApplication with search and pagination
        @Query("SELECT w FROM VersioningApplicationEntity w"+
                " WHERE LOWER (CAST(w.releaseDate AS string)) LIKE LOWER(CONCAT('%', :searchTerm, '%'))" +
                "    OR LOWER (w.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))"+
                "    OR LOWER (w.version) LIKE LOWER(CONCAT('%', :searchTerm, '%'))"+
                " ORDER BY w.idVersioningApplication DESC" )
        List<DatabaseEntity> findBySearchTerm(String searchTerm, Pageable pageable);

        //Getting data VersionApplication by UUID
        VersioningApplicationEntity findByUuid(UUID uuid);

        //Counting data VersionApplication with search
        @Query("SELECT COUNT(w) FROM VersioningApplicationEntity w " +
                "WHERE LOWER(CAST(w.releaseDate AS string)) LIKE LOWER(CONCAT('%', :searchTerm,'%'))"+
                "OR LOWER(w.version) LIKE LOWER(CONCAT('%', :searchTerm,'%'))"+
                "OR LOWER(w.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
        long countBySearchTerm(String searchTerm);

        //Updating data VersionApplication by UUID
        @Modifying
        @Transactional
        @Query("UPDATE VersioningApplicationEntity w SET " +
                "w.version = :version, w.description = :description, w.releaseDate = :releaseDate " +
                "WHERE w.uuid = :uuid")
        int findByUuidAndUpdate(UUID uuid, String version, String description, String releaseDate);

        //Deleting data VersionApplication by UUID
        @Modifying
        @Transactional
        @Query("DELETE FROM VersioningApplicationEntity w WHERE w.uuid = :uuid")
        int findByUuidAndDelete(UUID uuid);
}
