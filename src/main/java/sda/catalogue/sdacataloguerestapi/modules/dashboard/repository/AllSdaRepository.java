package sda.catalogue.sdacataloguerestapi.modules.dashboard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import sda.catalogue.sdacataloguerestapi.modules.dashboard.entity.DashboardEntity;

@Repository
public interface AllSdaRepository extends ViewNoIdRepository<DashboardEntity, Long>, JpaSpecificationExecutor<DashboardEntity> {
    Page<DashboardEntity> findAll(Specification<DashboardEntity> spec, Pageable pageable);
}
