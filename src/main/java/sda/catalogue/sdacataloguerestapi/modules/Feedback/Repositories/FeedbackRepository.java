package sda.catalogue.sdacataloguerestapi.modules.Feedback.Repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sda.catalogue.sdacataloguerestapi.modules.Feedback.Entities.FeedbackEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface FeedbackRepository extends JpaRepository<FeedbackEntity, Long> {
    @Query("SELECT w FROM FeedbackEntity w " +
            "WHERE (:year IS NULL OR YEAR(w.createdAt) = :year) " +
            "AND (:webAppEntityId IS NULL OR w.webAppEntity.idWebapp = :webAppEntityId) " +
            "AND (:rate IS NULL OR w.rate = :rate) " +
            "ORDER BY w.updatedAt DESC")
    List<FeedbackEntity> filterByParams(int year, Long webAppEntityId, Long rate, Pageable pageable);

    @Query("SELECT COUNT(w) FROM FeedbackEntity w " +
            "WHERE (:year IS NULL OR YEAR(w.createdAt) = :year) " +
            "AND (:webAppEntityId IS NULL OR w.webAppEntity.idWebapp = :webAppEntityId) " +
            "AND (:rate IS NULL OR w.rate = :rate)")
    long countByFilter(int year, Long webAppEntityId, Long rate);

    FeedbackEntity findByUuid(UUID uuid);

    @Modifying
    @Transactional
    @Query("UPDATE FeedbackEntity w SET " +
            "w.personalNumber = :personalNumber, " +
            "w.rate = :rate, " +
            "w.comment = :comment " +
            "WHERE w.uuid = :uuid")
    int findByUuidAndUpdate(UUID uuid, String personalNumber, Long rate, String comment);


    @Modifying
    @Transactional
    @Query("DELETE FROM FeedbackEntity w WHERE w.uuid = :uuid")
    int findByUuidAndDelete(UUID uuid);
}
