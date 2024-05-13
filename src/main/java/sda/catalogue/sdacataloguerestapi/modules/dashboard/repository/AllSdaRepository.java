package sda.catalogue.sdacataloguerestapi.modules.dashboard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sda.catalogue.sdacataloguerestapi.modules.dashboard.entity.DashboardEntity;

@Repository
public interface AllSdaRepository extends ViewNoIdRepository<DashboardEntity, Long> {
    Page<DashboardEntity> findAll(Specification<DashboardEntity> spec, Pageable pageable);

    /*
    * Create view query
    *
    * CREATE
        OR REPLACE VIEW all_sda_view AS
        SELECT
          mobileapp.id AS app_id,
          mapping_function,
          master_mf.id_mapping_function AS mapping_function_id,
          --   dept.dinas,
          --   mobileapp.role,
          cast(mobileapp.application_url as TEXT),
          mobileapp.status,
          mobileapp.application_name,
          mobileapp.business_impact_priority,
          mobileapp.created_at,
        --   m_fe.id_frontend AS frontend_id,
          m_fe.front_end,
        --   m_be.id_backend AS backend_id,
          m_be.back_end
        FROM
          tb_mapping_function master_mf
          JOIN mapping_functions mf ON master_mf.id_mapping_function = mf.id_mapping_function
          JOIN tb_mobileapp mobileapp ON mf.mobileapp_id = mobileapp.id
          --   JOIN tb_dinas_mapping_function dept ON master_mf.id_mapping_function = dept.id_mapping_function
          JOIN front_ends fe ON mobileapp.id = fe.mobileapp_id
          JOIN master_frontend m_fe ON fe.id_frontend = m_fe.id_frontend
          JOIN back_ends be ON mobileapp.id = be.mobileapp_id
          JOIN master_backend m_be ON be.id_backend = m_be.id_backend
        UNION ALL
        SELECT
          webapp.id_webapp AS app_id,
          mapping_function,
          master_mf.id_mapping_function AS mapping_function_id,
          --   dept.dinas,
          --   role,
          webapp.address,
          webapp.status,
          webapp.application_name,
          webapp.business_impact_priority,
          webapp.created_at,
        --   m_fe.id_frontend AS frontend_id,
          m_fe.front_end,
        --   m_be.id_backend AS backend_id,
          m_be.back_end
        FROM
          tb_mapping_function master_mf
          JOIN webapp_mapping_function w_mf ON master_mf.id_mapping_function = w_mf.id_mapping_function
          JOIN tb_webapp webapp ON w_mf.id_webapp = webapp.id_webapp
          --   JOIN tb_dinas_mapping_function dept ON master_mf.id_mapping_function = dept.id_mapping_function
          JOIN webapp_front_end w_fe ON webapp.id_webapp = w_fe.id_webapp
          JOIN master_frontend m_fe ON w_fe.id_frontend = m_fe.id_frontend
          JOIN webapp_back_end w_be ON webapp.id_webapp = w_be.id_webapp
          JOIN master_backend m_be ON w_be.id_backend = m_be.id_backend;
    * */
}
