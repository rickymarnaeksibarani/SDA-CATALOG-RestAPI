package sda.catalogue.sdacataloguerestapi.modules.mobileapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sda.catalogue.sdacataloguerestapi.core.enums.Status;
import sda.catalogue.sdacataloguerestapi.modules.mobileapp.entity.MobileAppEntity;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface MobileAppRepository extends JpaRepository<MobileAppEntity, Long>, JpaSpecificationExecutor<MobileAppEntity> {
    Boolean existsByApplicationName(String applicationName);

    Integer countAllByStatus(Status status);

    @Query("SELECT unnest(m.sdaHosting) AS hosting, COUNT(m.id) AS total FROM MobileAppEntity m GROUP BY hosting")
    List<Object[]> countAllBySdaHosting();
}
