package sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Entities.FrontEndEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface FrontEndRepository extends JpaRepository<FrontEndEntity, Long> {

    //Getting data Front End with search and pagination
    @Query("SELECT w FROM FrontEndEntity w " +
            "WHERE LOWER(w.frontEnd) LIKE LOWER(CONCAT('%', :searchTerm,'%')) " +
            "ORDER BY w.updatedAt DESC")
    List<FrontEndEntity> findBySearchTerm(String searchTerm, Pageable pageable);

    //Counting data Front End with search
    @Query("SELECT COUNT(w) FROM FrontEndEntity w " +
            "WHERE LOWER(w.frontEnd) LIKE LOWER(CONCAT('%', :searchTerm,'%'))")
    long countBySearchTerm(String searchTerm);

    //Getting data Front End by UUID
    FrontEndEntity findByUuid(UUID uuid);

    //Updating data Front End by UUID
    @Modifying
    @Transactional
    @Query("UPDATE FrontEndEntity w SET " +
            "w.frontEnd = :frontEnd " +
            "WHERE w.uuid = :uuid")
    FrontEndEntity findByUuidAndUpdate(
            UUID uuid,
            String frontEnd
    );

    //Deleting data Front End by UUID
    @Modifying
    @Transactional
    @Query("DELETE FROM FrontEndEntity w WHERE w.uuid = :uuid")
    FrontEndEntity findByUuidAndDelete(UUID uuid);
}
