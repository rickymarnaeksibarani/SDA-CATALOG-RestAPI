package sda.catalogue.sdacataloguerestapi.modules.Feedback.Repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sda.catalogue.sdacataloguerestapi.modules.Feedback.Entities.CommentEntity;

import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long>, JpaSpecificationExecutor {
    @Modifying
    @Transactional
    @Query("UPDATE CommentEntity w SET " +
            "w.personalNumber = :personalNumber, " +
            "w.comment = :comment " +
            "WHERE w.uuid = :uuid")
    int findByUuidAndUpdate(UUID uuid, String personalNumber, String comment);


    CommentEntity findByUuid(UUID uuid);

    @Modifying
    @Transactional
    @Query("DELETE FROM CommentEntity w WHERE w.uuid = :uuid")
    int findByUuidAndDelete(UUID uuid);
}
