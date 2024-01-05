package sda.catalogue.sdacataloguerestapi.modules.WebApp.Repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Entities.BackEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.DocumentUpload.Entities.DocumentUploadEntity;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Entities.FrontEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Entities.MappingFunctionEntity;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Entities.PICDeveloperEntity;
import sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Entities.SDAHostingEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.DatabaseEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.VersioningApplicationEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.WebAppEntity;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;
import sda.catalogue.sdacataloguerestapi.modules.WebServer.Entities.WebServerEntity;

@Repository
public interface WebAppRepository extends JpaRepository<WebAppEntity, Long> {

    //Getting data WebApp with search and pagination
    @Query("SELECT w FROM WebAppEntity w " +
            "WHERE LOWER(w.applicationName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.functionApplication) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.address) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.businessImpactPriority) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(w.status) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "ORDER BY w.updatedAt DESC")
    List<WebAppEntity> findBySearchTerm(String searchTerm, Pageable pageable);

    //Counting data WebApp with search
    @Query("SELECT COUNT(w) FROM WebAppEntity w " +
            "WHERE LOWER(w.applicationName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
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
    @Query("UPDATE WebAppEntity w SET w.applicationName = :applicationName, w.categoryApp = :categoryApp, " +
            "w.description = :description, w.functionApplication = :functionApplication, w.address = :address, " +
            "w.businessImpactPriority = :businessImpactPriority, w.status = :status, w.linkIOS = :linkIOS, " +
            "w.linkAndroid = :linkAndroid, w.fileManifest = :fileManifest, w.fileIpa = :fileIpa, " +
            "w.fileAndroid = :fileAndroid, w.applicationSourceFe = :applicationSourceFe, " +
            "w.applicationSourceBe = :applicationSourceBe, w.ipDatabase = :ipDatabase " +
            "WHERE w.uuid = :uuid")
    int updateByUuid(UUID uuid,
                           String applicationName,
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

    //Deleting data WebApp by UUID
    @Modifying
    @Query("DELETE FROM WebAppEntity w WHERE w.uuid = :uuid")
    WebAppEntity findByUuidAndDelete(UUID uuid);
}
