package sda.catalogue.sdacataloguerestapi.modules.WebApp.Repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sda.catalogue.sdacataloguerestapi.core.enums.SapIntegration;
import sda.catalogue.sdacataloguerestapi.core.enums.Status;
import sda.catalogue.sdacataloguerestapi.modules.DocumentUpload.Entities.DocumentUploadEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.WebAppEntity;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;

@Repository
public interface WebAppRepository extends JpaRepository<WebAppEntity, Long>, JpaSpecificationExecutor<WebAppEntity> {
    //Getting data WebApp with search and pagination
    @Query("SELECT w FROM WebAppEntity w " +
            "WHERE LOWER(w.applicationName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.assetNumber) LIKE LOWER(CONCAT('%', :searchTerm, '%'))" +
            "   OR LOWER(w.sapIntegration) LIKE LOWER(CONCAT('%', :searchTerm, '%'))" +
            "   OR LOWER(w.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.functionApplication) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.address) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.businessImpactPriority) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.status) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "ORDER BY CASE WHEN :order = 'asc' THEN :by END ASC, CASE WHEN :order = 'desc' THEN :by END DESC")
    List<WebAppEntity> findBySearchTerm(String searchTerm, String order, String by, Pageable pageable);

    //Counting data WebApp with search
    @Query("SELECT COUNT(w) FROM WebAppEntity w " +
            "WHERE LOWER(w.applicationName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.assetNumber) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.sapIntegration) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.functionApplication) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.address) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.businessImpactPriority) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.status) LIKE LOWER(CONCAT('%', :searchTerm, '%')) ")
    long countBySearchTerm(String searchTerm);

    @Modifying
    @Transactional
    @Query("DELETE FROM WebAppEntity w WHERE w.uuid = :uuid")
    void findByUuidAndDelete(UUID uuid);

    @Query("SELECT COUNT(w) FROM WebAppEntity w WHERE w.status = :status")
    int countByStatus(String status);

    boolean existsByApplicationName(String applicationName);

    Integer countAllByStatus(Status status);

    @Query(value = """
            SELECT
              hosting.sda_hosting AS name,
              COUNT(webapp.id_sda_hosting)
            FROM
              master_sda_hosting hosting
              INNER JOIN tb_webapp webapp ON hosting.id_sda_hosting = webapp.id_sda_hosting
            GROUP BY
              name;""", nativeQuery = true)
    List<Object[]> countAllBySdaHosting();

    WebAppEntity findByApplicationName(String applicationName);

    boolean existsByAssetNumber(String assetNumber);
}
