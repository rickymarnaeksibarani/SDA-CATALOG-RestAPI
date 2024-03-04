package sda.catalogue.sdacataloguerestapi.modules.mobileapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import sda.catalogue.sdacataloguerestapi.modules.mobileapp.entity.MobileAppEntity;

import java.util.Optional;

@Repository
public interface MobileAppRepository extends JpaRepository<MobileAppEntity, Long>, JpaSpecificationExecutor<MobileAppEntity> {
    Boolean existsByApplicationName(String applicationName);
}
