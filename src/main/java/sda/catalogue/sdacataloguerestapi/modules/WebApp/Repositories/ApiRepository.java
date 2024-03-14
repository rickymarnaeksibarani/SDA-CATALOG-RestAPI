package sda.catalogue.sdacataloguerestapi.modules.WebApp.Repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.ApiEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface ApiRepository extends JpaRepository<ApiEntity, Long>, JpaSpecificationExecutor<ApiEntity> {
    //Getting data API with search and pagination
    @Query("SELECT w FROM ApiEntity w"+
            " WHERE LOWER (w.apiName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))" +
            "    OR LOWER (w.ipApi) LIKE LOWER(CONCAT('%', :searchTerm, '%'))"+
            "    OR LOWER (w.userName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))"+
            "    OR LOWER (w.password) LIKE LOWER(CONCAT('%', :searchTerm, '%'))"+
            " ORDER BY w.idApi DESC" )  
    List<ApiEntity> findBySearchTerm(String searchTerm, Pageable pageable);

    //Counting data API with search
    @Query("SELECT COUNT(w) FROM ApiEntity w " +
            "WHERE LOWER(w.apiName) LIKE LOWER(CONCAT('%', :searchTerm,'%'))"+
            "OR LOWER(w.ipApi) LIKE LOWER(CONCAT('%', :searchTerm,'%'))"+
            "OR LOWER(w.userName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))"+
            "OR LOWER(w.password) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    long countBySearchTerm(String searchTerm);

    //Getting data API by UUID
    ApiEntity findByUuid(UUID uuid);

    //Updating data API by UUID
    @Modifying
    @Transactional
    @Query("UPDATE ApiEntity w SET " +
            "w.apiName = :apiName, w.userName = :userName, w.password = :password, w.ipApi = :ipAddress " +
            "WHERE w.uuid = :uuid")
    int findByUuidAndUpdate(UUID uuid, String apiName, String userName, String password, String ipAddress);


    //Deleting data API by UUID
    @Modifying
    @Transactional
    @Query("DELETE FROM ApiEntity w WHERE w.uuid = :uuid")
    int findByUuidAndDelete(UUID uuid);
}
