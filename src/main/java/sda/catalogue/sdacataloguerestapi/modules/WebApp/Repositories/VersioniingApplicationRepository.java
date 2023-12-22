package sda.catalogue.sdacataloguerestapi.modules.WebApp.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.VersioningApplicationEntity;

@Repository
public interface VersioniingApplicationRepository extends JpaRepository<VersioningApplicationEntity, Long> {

}
