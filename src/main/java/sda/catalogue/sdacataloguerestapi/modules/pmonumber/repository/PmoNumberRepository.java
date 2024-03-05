package sda.catalogue.sdacataloguerestapi.modules.pmonumber.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Entities.FrontEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.pmonumber.entity.PmoNumberEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface PmoNumberRepository extends JpaRepository<PmoNumberEntity, Long>, JpaSpecificationExecutor<PmoNumberEntity> {
    //Getting data PMO Nu,ber with search and pagination
    @Query("SELECT w FROM PmoNumberEntity w " +
            "WHERE LOWER(w.pmoNumber) LIKE LOWER(CONCAT('%', :searchTerm,'%')) " +
            "ORDER BY w.updatedAt DESC")
    List<PmoNumberEntity> findBySearchTerm(String searchTerm, Pageable pageable);

    //Counting data PMO Number with search
    @Query("SELECT COUNT(w) FROM PmoNumberEntity w " +
            "WHERE LOWER(w.pmoNumber) LIKE LOWER(CONCAT('%', :searchTerm,'%'))")
    long countBySearchTerm(String searchTerm);

    //Getting data PMO Number by UUID
    PmoNumberEntity findByUuid(UUID uuid);

    //Updating data PMO Number by UUID
    @Modifying
    @Transactional
    @Query("UPDATE PmoNumberEntity w SET " +
            "w.pmoNumber = :pmoNumber " +
            "WHERE w.uuid = :uuid")
    int findByUuidAndUpdate(
            UUID uuid,
            String pmoNumber
    );

    //Deleting data PMO Number by UUID
    @Modifying
    @Transactional
    @Query("DELETE FROM PmoNumberEntity w WHERE w.uuid = :uuid")
    int findByUuidAndDelete(UUID uuid);
}
