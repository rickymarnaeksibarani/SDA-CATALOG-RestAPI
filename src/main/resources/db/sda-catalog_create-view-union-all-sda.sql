DROP VIEW IF EXISTS all_sda_view;

-- CREATE VIEW
CREATE VIEW all_sda_view AS SELECT
    ROW_NUMBER() OVER () AS id,
--               *
    x.app_id,
    x.application_url,
    x.status,
    x.application_name,
    x.business_impact_priority,
    x.app_category,
    x.created_at,
    json_agg(x.mapping_function) AS mapping_func_list,
    json_agg(x.depts) AS dept_list,
    json_agg(x.fe_list) AS fe_list,
    json_agg(x.be_list) AS be_list
FROM (
    SELECT
        mobileapp.id AS app_id,
        --   mobileapp.role,
        mobileapp.application_url AS application_url,
        mobileapp.status,
        mobileapp.application_name,
        mobileapp.business_impact_priority,
        mobileapp.app_category,
        mobileapp.created_at,
        mappings.mapping_function,
        cast(depts as jsonb) AS depts,
        json_agg(fe_list) AS fe_list,
        json_agg(be_list) AS be_list
    FROM
        tb_mobileapp mobileapp
            LEFT JOIN mapping_functions mf ON mobileapp.id = mf.mobileapp_id
            LEFT JOIN (
            SELECT m_mf.id_mapping_function, m_mf.mapping_function, json_agg(depts.*) AS depts
            FROM tb_mapping_function m_mf
                     LEFT JOIN (SELECT dept.id_dinas, dept.id_mapping_function AS mapping_function_id, dept.dinas
                                FROM tb_dinas_mapping_function dept
            ) depts ON depts.mapping_function_id = m_mf.id_mapping_function
            GROUP BY
                m_mf.id_mapping_function,
                m_mf.mapping_function
        ) mappings ON mappings.id_mapping_function = mf.id_mapping_function
            LEFT JOIN front_ends fe ON mobileapp.id = fe.mobileapp_id
            LEFT JOIN (
            SELECT m_fe.id_frontend AS fe_id, m_fe.front_end AS fe_name
            FROM master_frontend m_fe
        ) fe_list ON fe_list.fe_id = fe.id_frontend
            LEFT JOIN back_ends be ON mobileapp.id = be.mobileapp_id
            LEFT JOIN (
            SELECT m_be.id_backend AS be_id, m_be.back_end AS be_name
            FROM master_backend m_be
        ) be_list ON be_list.be_id = be.id_backend
    GROUP BY app_id, mappings.mapping_function, cast(mappings.depts as jsonb)
    UNION ALL
    SELECT
        webapp.id_webapp AS app_id,
        --   webapp.role,
        webapp.address AS application_url,
        webapp.status,
        webapp.application_name,
        webapp.business_impact_priority,
        webapp.app_category,
        webapp.created_at,
        mappings.mapping_function,
        cast(depts as jsonb) AS depts,
        json_agg(fe_list) AS fe_list,
        json_agg(be_list) AS be_list
    FROM
        tb_webapp webapp
            LEFT JOIN webapp_mapping_function mf ON webapp.id_webapp = mf.id_webapp
            LEFT JOIN (
            SELECT m_mf.id_mapping_function, m_mf.mapping_function, json_agg(depts.*) AS depts
            FROM tb_mapping_function m_mf
                     LEFT JOIN (SELECT dept.id_dinas, dept.id_mapping_function AS mapping_function_id, dept.dinas
                                FROM tb_dinas_mapping_function dept
            ) depts ON depts.mapping_function_id = m_mf.id_mapping_function
            GROUP BY
                m_mf.id_mapping_function,
                m_mf.mapping_function
        ) mappings ON mappings.id_mapping_function = mf.id_mapping_function
            LEFT JOIN webapp_front_end fe ON webapp.id_webapp = fe.id_webapp
            LEFT JOIN (
            SELECT m_fe.id_frontend AS fe_id, m_fe.front_end AS fe_name
            FROM master_frontend m_fe
        ) fe_list ON fe_list.fe_id = fe.id_frontend
            LEFT JOIN webapp_back_end be ON webapp.id_webapp = be.id_webapp
            LEFT JOIN (
            SELECT m_be.id_backend AS be_id, m_be.back_end AS be_name
            FROM master_backend m_be
        ) be_list ON be_list.be_id = be.id_backend
    GROUP BY app_id, mappings.mapping_function, cast(mappings.depts as jsonb)
) x
GROUP BY x.app_id,x.application_url,x.status,x.application_name,x.business_impact_priority,x.app_category,x.created_at;