package sda.catalogue.sdacataloguerestapi.modules.mobileapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import sda.catalogue.sdacataloguerestapi.core.enums.Status;
import sda.catalogue.sdacataloguerestapi.modules.mobileapp.entity.MobileAppEntity;

import java.util.Collection;
import java.util.List;

@Repository
public interface MobileAppRepository extends JpaRepository<MobileAppEntity, Long>, JpaSpecificationExecutor<MobileAppEntity> {
    Boolean existsByApplicationName(String applicationName);

    Integer countAllByStatus(Status status);

    List<MobileAppEntity> countAllBySdaHostingIn(Collection<String> name);
}
