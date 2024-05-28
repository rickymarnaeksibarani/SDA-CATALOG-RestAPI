package sda.catalogue.sdacataloguerestapi.modules.dashboard.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Subselect;
import sda.catalogue.sdacataloguerestapi.core.enums.AppCategory;
import sda.catalogue.sdacataloguerestapi.core.enums.BusinessImpactPriority;
import sda.catalogue.sdacataloguerestapi.core.enums.Status;

import java.time.LocalDateTime;

@Data
@Entity
@Subselect("SELECT * FROM all_sda_view")
public class DashboardEntity {
    @Id
    private Long id;

    private Long appId;

    @Column(name = "application_url")
    private String applicationUrl;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "application_name")
    private String applicationName;

    @Column(name = "business_impact_priority")
    @Enumerated(EnumType.STRING)
    private BusinessImpactPriority businessImpactPriority;

    @Enumerated(EnumType.STRING)
    private AppCategory appCategory;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "mapping_func_list", columnDefinition = "jsonb")
    private String mappingFunctionList;

    @Column(name = "dept_list", columnDefinition = "jsonb")
    private String deptList;

    @Column(name = "fe_list", columnDefinition = "jsonb")
    private String feList;

    @Column(name = "be_list", columnDefinition = "jsonb")
    private String beList;
}
