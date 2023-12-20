package sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Entities.SDAHostingEntity;

public interface SDAHostingRepository extends JpaRepository<SDAHostingEntity, Long> {

}
