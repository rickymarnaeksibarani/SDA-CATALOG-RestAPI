package sda.catalogue.sdacataloguerestapi.modules.WebApp.Repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sda.catalogue.sdacataloguerestapi.core.enums.SapIntegration;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.WebAppEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;

@Repository
public interface WebAppRepository extends JpaRepository<WebAppEntity, Long>, JpaSpecificationExecutor<WebAppEntity> {
    //Getting data WebApp with search and pagination
    @Query("SELECT w FROM WebAppEntity w " +
            "WHERE LOWER(w.applicationName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.pmoNumber) LIKE LOWER(CONCAT('%', :searchTerm, '%'))" +
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
            "   OR LOWER(w.pmoNumber) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.sapIntegration) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.functionApplication) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.address) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.businessImpactPriority) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.status) LIKE LOWER(CONCAT('%', :searchTerm, '%')) ")
    long countBySearchTerm(String searchTerm);

    //Getting data WebApp by UUID
    WebAppEntity findByUuid(UUID uuid);


    //Updating data WebApp by UUID
    @Modifying
    @Transactional
    @Query("UPDATE WebAppEntity w SET w.applicationName = :applicationName, w.pmoNumber = :pmoNumber, w.sapIntegration = :sapIntegration,w.categoryApp = :categoryApp, " +
            "w.description = :description, w.functionApplication = :functionApplication, w.address = :address, " +
            "w.businessImpactPriority = :businessImpactPriority, w.status = :status, w.linkIOS = :linkIOS, " +
            "w.linkAndroid = :linkAndroid, w.fileManifest = :fileManifest, w.fileIpa = :fileIpa, " +
            "w.fileAndroid = :fileAndroid, w.applicationSourceFe = :applicationSourceFe, " +
            "w.applicationSourceBe = :applicationSourceBe, w.ipDatabase = :ipDatabase " +
            "WHERE w.uuid = :uuid")
    int updateByUuid(UUID uuid,
                     String applicationName,
                     String pmoNumber,
                     SapIntegration sapIntegration,
                     String categoryApp,
                     String description,
                     String functionApplication,
                     String address,
                     String businessImpactPriority,
                     String status,
                     String linkIOS,
                     String linkAndroid,
                     String fileManifest,
                     String fileIpa,
                     String fileAndroid,
                     String applicationSourceFe,
                     String applicationSourceBe,
                     String ipDatabase);

    @Modifying
    @Transactional
    @Query("DELETE FROM WebAppEntity w WHERE w.uuid = :uuid")
    void findByUuidAndDelete(UUID uuid);

    @Query("SELECT COUNT(w) FROM WebAppEntity w WHERE w.status = :status")
    int countByStatus(String status);

//    @Query("SELECT COUNT(w) FROM WebAppEntity w WHERE w.sdaHostingEntity.sdaHosting = :sdaHosting")
//    Long countBySdaHosting(String sdaHosting);

    boolean existsByApplicationName(String applicationName);
}
