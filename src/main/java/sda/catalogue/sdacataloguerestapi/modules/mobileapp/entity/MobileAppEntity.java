package sda.catalogue.sdacataloguerestapi.modules.mobileapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import sda.catalogue.sdacataloguerestapi.core.enums.AppCategory;
import sda.catalogue.sdacataloguerestapi.core.enums.BusinessImpactPriority;
import sda.catalogue.sdacataloguerestapi.core.enums.SapIntegration;
import sda.catalogue.sdacataloguerestapi.core.enums.Status;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Entities.BackEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Entities.FrontEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Entities.MappingFunctionEntity;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Entities.PICDeveloperEntity;
import sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Entities.SDAHostingEntity;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_mobileapp")
@EntityListeners({AuditingEntityListener.class})
public class MobileAppEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "application_name", nullable = false)
    private String applicationName;

    @Column(name = "pmo_number", nullable = false)
    private String pmoNumber;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private Status status;

    private String role;

    @Enumerated(EnumType.STRING)
    @Column(name = "business_impact_priority", nullable = false)
    private BusinessImpactPriority businessImpactPriority;

    @Column(nullable = false, columnDefinition = "text")
    private String description;

    @Column(name = "application_function", nullable = false)
    private String applicationFunction;

    private String documentation;

    @Column(name = "versioning_application", nullable = false, columnDefinition = "text")
    private String versioningApplication;

    @Column(name = "application_url", columnDefinition = "text")
    private String applicationUrl;

    @Column(name = "application_file_path", columnDefinition = "text")
    private String applicationFile;

    @Column(name = "web_server", nullable = false)
    private String webServer;

    @Enumerated(EnumType.STRING)
    @Column(name = "sap_integration", nullable = false)
    private SapIntegration sapIntegration;

    @Column(name = "application_source_front_end", nullable = false)
    private String applicationSourceFrontEnd;

    @Column(name = "application_source_back_end", nullable = false)
    private String applicationSourceBackEnd;

    @Column(name = "database_ip", nullable = false)
    private String databaseIp;

    @Column(name = "application_api_list", columnDefinition = "text")
    private String applicationApiList;

    @Column(name = "application_database_list", columnDefinition = "text")
    private String applicationDatabaseList;

    @Enumerated(EnumType.STRING)
    @Column(name = "app_category", nullable = false)
    private AppCategory appCategory = AppCategory.MOBILE;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(
            name = "mapping_functions",
            joinColumns = @JoinColumn(name = "mobileapp_id"),
            inverseJoinColumns = @JoinColumn(name = "id_mapping_function")
    )
    private List<MappingFunctionEntity> mappingFunctions;

    @ManyToMany
    @JoinTable(
            name = "pic_developers",
            joinColumns = @JoinColumn(name = "mobileapp_id"),
            inverseJoinColumns = @JoinColumn(name = "id_pic_developer")
    )
    private List<PICDeveloperEntity> picDevelopers;

    @ManyToMany
    @JoinTable(
            name = "front_ends",
            joinColumns = @JoinColumn(name = "mobileapp_id"),
            inverseJoinColumns = @JoinColumn(name = "id_frontend")
    )
    private List<FrontEndEntity> frontEnds;

    @ManyToMany
    @JoinTable(
            name = "back_ends",
            joinColumns = @JoinColumn(name = "mobileapp_id"),
            inverseJoinColumns = @JoinColumn(name = "id_backend")
    )
    private List<BackEndEntity> backEnds;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "sda_hosting_list",
            joinColumns = @JoinColumn(name = "mobileapp_id"),
            inverseJoinColumns = @JoinColumn(name = "id_sda_hosting")
    )
    private List<SDAHostingEntity> sdaHostingList;
}
