package sda.catalogue.sdacataloguerestapi.modules.mobileapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import sda.catalogue.sdacataloguerestapi.core.enums.BusinessImpactPriority;
import sda.catalogue.sdacataloguerestapi.core.enums.Role;
import sda.catalogue.sdacataloguerestapi.core.enums.SapIntegration;
import sda.catalogue.sdacataloguerestapi.core.enums.Status;

import java.time.LocalDateTime;

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

//    @ManyToOne()
//    @JoinColumn(name = "sda_hosting_id", referencedColumnName = "id_sda_hosting")
//    private SDAHostingEntity sdaHosting;

    private String sdaHosting;

//    @ManyToOne
//    @JoinColumn(name = "mapping_function_id", referencedColumnName = "id_mapping_function")
//    private MappingFunctionEntity mappingFunction;

    private String mappingFunction;

    @Column(nullable = false, columnDefinition = "json")
    private String department;

    @Column(nullable = false)
//    @Enumerated(EnumType.STRING)
    private String role;

    @Enumerated(EnumType.STRING)
    @Column(name = "business_impact_priority", nullable = false)
    private BusinessImpactPriority businessImpactPriority;

    @Column(name = "pic_developer", nullable = false, columnDefinition = "json")
    private String picDeveloper;

    @Column(nullable = false, columnDefinition = "text")
    private String description;

    @Column(name = "application_function", nullable = false)
    private String applicationFunction;

    private String documentation;

    @Column(name = "versioning_application", nullable = false, columnDefinition = "json")
    private String versioningApplication;

    @Column(name = "application_url", columnDefinition = "json")
    private String applicationUrl;

    @Column(name = "application_file_path", columnDefinition = "json")
    private String applicationFile;

    @Column(name = "sda_front_end", nullable = false)
    private String sdaFrontEnd;

    @Column(name = "sda_back_end", nullable = false)
    private String sdaBackEnd;

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

    @Column(name = "application_api_list", columnDefinition = "json")
    private String applicationApiList;

    @Column(name = "application_database_list", columnDefinition = "json")
    private String applicationDatabaseList;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
