package sda.catalogue.sdacataloguerestapi.modules.WebServer.Repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Entities.BackEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebServer.Entities.WebServerEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WebServerRepository extends JpaRepository<WebServerEntity, Long>, JpaSpecificationExecutor<WebServerEntity> {

    @Query("SELECT w FROM WebServerEntity w " +
            "WHERE LOWER(w.webServer) LIKE LOWER(CONCAT('%', :searchTerm,'%')) " +
            "ORDER BY w.updatedAt DESC")
    List<WebServerEntity> findBySearchTerm(String searchTerm, Pageable pageable);

    @Query("SELECT COUNT(w) FROM WebServerEntity w " +
            "WHERE LOWER(w.webServer) LIKE LOWER(CONCAT('%', :searchTerm,'%'))")
    long countBySearchTerm(String searchTerm);

    Optional <WebServerEntity> findByUuid(UUID uuid);

    @Modifying
    @Transactional
    @Query("UPDATE WebServerEntity w SET " +
            "w.webServer = :webServer " +
            "WHERE w.uuid = :uuid")
    int findByUuidAndUpdate(
            UUID uuid,
            String webServer
    );

    @Modifying
    @Transactional
    @Query("DELETE FROM WebServerEntity w WHERE w.uuid = :uuid")
    int findByUuidAndDelete(UUID uuid);

    List<WebServerEntity> findByIdWebServerIsIn(Collection<Long> id);

    boolean existsByWebServer(String webServer);
}
