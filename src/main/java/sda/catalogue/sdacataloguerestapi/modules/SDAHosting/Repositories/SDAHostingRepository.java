package sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Entities.SDAHostingEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface SDAHostingRepository extends JpaRepository<SDAHostingEntity, Long>, JpaSpecificationExecutor<SDAHostingEntity> {
    @Query("SELECT w FROM SDAHostingEntity w " +
            "WHERE LOWER(w.sdaHosting) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "ORDER BY w.updatedAt DESC")
    List<SDAHostingEntity> findBySearchTerm(String searchTerm, Pageable pageable);


    @Query("SELECT COUNT(w) FROM SDAHostingEntity w " +
            "WHERE LOWER(w.sdaHosting) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    long countBySearchTerm(String searchTerm);

    SDAHostingEntity findByUuid(UUID uuid);

    @Modifying
    @Transactional
    @Query("UPDATE SDAHostingEntity w SET " +
            "w.sdaHosting = :sdaHosting " +
            "WHERE w.uuid = :uuid")
    int findByUuidAndUpdate(
            UUID uuid,
            String sdaHosting
    );

    @Modifying
    @Transactional
    @Query("DELETE FROM SDAHostingEntity w WHERE w.uuid = :uuid")
    int findByUuidAndDelete(UUID uuid);
}
