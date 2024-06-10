package sda.catalogue.sdacataloguerestapi.modules.DocumentUpload.Repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sda.catalogue.sdacataloguerestapi.modules.DocumentUpload.Entities.DocumentUploadEntity;
import sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Entities.TypeDatabaseEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.WebAppEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface DocumentUploadRepository extends JpaRepository<DocumentUploadEntity,Long>, JpaSpecificationExecutor<DocumentUploadEntity> {

 //Getting data DocumentUpload with search and pagination
 @Query("SELECT w FROM DocumentUploadEntity w " +
         "WHERE LOWER(w.path) LIKE LOWER(CONCAT('%', :searchTerm,'%')) " +
         "ORDER BY w.updatedAt DESC")
 List<DocumentUploadEntity> findBySearchTerm(String searchTerm, Pageable pageable);

 //Counting data Document Upload with search
 @Query("SELECT COUNT(w) FROM DocumentUploadEntity w " +
         "WHERE LOWER(w.path) LIKE LOWER(CONCAT('%', :searchTerm,'%'))")
 long countBySearchTerm(String searchTerm);

 //Getting data Document Upload by UUID
 DocumentUploadEntity findByUuid(UUID uuid);

 //Updating data Document Upload by UUID
 @Modifying
 @Transactional
 @Query("UPDATE DocumentUploadEntity w SET " +
         "w.path = :path " +
         "WHERE w.uuid = :uuid")
 int findByUuidAndUpdate(UUID uuid, String path);

 //Deleting data Document Upload by UUID
 @Modifying
 @Transactional
 @Query("DELETE FROM DocumentUploadEntity w WHERE w.uuid = :uuid")
 int findByUuidAndDelete(UUID uuid);

 void deleteByWebAppEntityAndPathIn(WebAppEntity findData, List<String> removedDocPaths);

 void deleteByPath(@Param("path") String path);
}