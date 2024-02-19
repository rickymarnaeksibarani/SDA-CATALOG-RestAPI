package sda.catalogue.sdacataloguerestapi.modules.WebApp.Repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Entities.TypeDatabaseEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.DatabaseEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface DatabaseRepository extends JpaRepository<DatabaseEntity, Long> {
    //Getting data Database with search and pagination
    @Query("SELECT w FROM DatabaseEntity w"+
            " WHERE LOWER (w.apiName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))" +
            "    OR LOWER (w.apiAddress) LIKE LOWER(CONCAT('%', :searchTerm, '%'))"+
            "    OR LOWER (w.userName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))"+
            "    OR LOWER (w.password) LIKE LOWER(CONCAT('%', :searchTerm, '%'))"+
            " ORDER BY w.idDatabase DESC" )
    List<DatabaseEntity> findBySearchTerm(String searchTerm, Pageable pageable);

    //Counting data Database with search
    @Query("SELECT COUNT(w) FROM DatabaseEntity w " +
            "WHERE LOWER(w.apiName) LIKE LOWER(CONCAT('%', :searchTerm,'%'))"+
            "OR LOWER(w.apiAddress) LIKE LOWER(CONCAT('%', :searchTerm,'%'))"+
            "OR LOWER(w.userName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))"+
            "OR LOWER(w.password) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    long countBySearchTerm(String searchTerm);

    //Getting data Database by UUID
    DatabaseEntity findByUuid(UUID uuid);

    //Updating data Database by UUID
    @Modifying
    @Transactional
    @Query("UPDATE DatabaseEntity w SET " +
            "w.apiName = :apiName, w.userName = :userName, w.password = :password, w.apiAddress = :ipAddress " +
            "WHERE w.uuid = :uuid")
    int findByUuidAndUpdate(UUID uuid, String apiName, String userName, String password, String ipAddress);


    //Deleting data Database by UUID
    @Modifying
    @Transactional
    @Query("DELETE FROM DatabaseEntity w WHERE w.uuid = :uuid")
    int findByUuidAndDelete(UUID uuid);
}
