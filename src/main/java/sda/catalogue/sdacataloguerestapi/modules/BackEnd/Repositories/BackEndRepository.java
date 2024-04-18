package sda.catalogue.sdacataloguerestapi.modules.BackEnd.Repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Entities.BackEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Entities.FrontEndEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BackEndRepository extends JpaRepository<BackEndEntity, Long>, JpaSpecificationExecutor<BackEndEntity> {
    //Getting data Back End with search and pagination
    @Query("SELECT w FROM BackEndEntity w " +
            "WHERE LOWER(w.backEnd) LIKE LOWER(CONCAT('%', :searchTerm,'%')) " +
            "ORDER BY w.updatedAt DESC")
    Page<BackEndEntity> findBySearchTerm(String searchTerm, Pageable pageable);

    //Counting data Back End with search
    @Query("SELECT COUNT(w) FROM BackEndEntity w " +
            "WHERE LOWER(w.backEnd) LIKE LOWER(CONCAT('%', :searchTerm,'%'))")
    long countBySearchTerm(String searchTerm);

    //Getting data Back End by UUID
    Optional<BackEndEntity> findByUuid(UUID uuid);

    //Updating data Back End by UUID
    @Modifying
    @Transactional
    @Query("UPDATE BackEndEntity w SET " +
            "w.backEnd = :backEnd " +
            "WHERE w.uuid = :uuid")
    int findByUuidAndUpdate(
            UUID uuid,
            String backEnd
    );

    //Deleting data Back End by UUID
    @Modifying
    @Transactional
    @Query("DELETE FROM BackEndEntity w WHERE w.uuid = :uuid")
    int findByUuidAndDelete(UUID uuid);

    List<BackEndEntity> findByBackEndIsIn(Collection<String> name);

    List<BackEndEntity> findByIdBackEndIsIn(Collection<Long> id);

    boolean existsByBackEnd(String backEnd);
}
