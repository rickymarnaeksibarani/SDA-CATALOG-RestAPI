package sda.catalogue.sdacataloguerestapi.modules.mobileapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sda.catalogue.sdacataloguerestapi.core.enums.Status;
import sda.catalogue.sdacataloguerestapi.modules.mobileapp.entity.MobileAppEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface MobileAppRepository extends JpaRepository<MobileAppEntity, Long>, JpaSpecificationExecutor<MobileAppEntity> {
    boolean existsByApplicationName(String applicationName);

    Integer countAllByStatus(Status status);

    @Query(value = """
            SELECT
              hosting.sda_hosting AS name,
              COUNT(hosting_list.mobileapp_id)
            FROM
              master_sda_hosting hosting
              INNER JOIN sda_hosting_list hosting_list ON hosting.id_sda_hosting = hosting_list.id_sda_hosting
            GROUP BY
              name;""", nativeQuery = true)
    List<Object[]> countAllBySdaHosting();

    Optional<MobileAppEntity> findByApplicationName(String applicationName);
}
