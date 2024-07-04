package sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import sda.catalogue.sdacataloguerestapi.core.enums.AppCategory;
import sda.catalogue.sdacataloguerestapi.core.enums.BusinessImpactPriority;
import sda.catalogue.sdacataloguerestapi.core.enums.SapIntegration;
import sda.catalogue.sdacataloguerestapi.core.enums.Status;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Entities.BackEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.DocumentUpload.Entities.DocumentUploadEntity;
import sda.catalogue.sdacataloguerestapi.modules.Feedback.Entities.FeedbackEntity;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Entities.FrontEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Entities.MappingFunctionEntity;
import sda.catalogue.sdacataloguerestapi.modules.PICAnalyst.Entities.PICAnalystEntity;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Entities.PICDeveloperEntity;
//import sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Entities.SDAHostingEntity;
import sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Entities.SDAHostingEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebServer.Entities.WebServerEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "tb_webapp")
@EntityListeners({AuditingEntityListener.class})
public class WebAppEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_webapp")
    private Long idWebapp;

    @UuidGenerator
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(name = "application_name", nullable = false)
    private String applicationName;

    @Column(name = "asset_number", nullable = false, length = 50, unique = true)
    private String assetNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "sap_integration", nullable = false)
    private SapIntegration sapIntegration;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "app_category", nullable = false)
    private AppCategory appCategory = AppCategory.WEB;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "function_application", columnDefinition = "text")
    private String functionApplication;

    @Column(name = "versioning_application_list", columnDefinition = "text")
    private String versioningApplicationList;

    @Column(name = "api_application_list", columnDefinition = "text")
    private String apiApplicationList;

    @Column(name = "database_application_list", columnDefinition = "text")
    private String databaseList;

    @Column(name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "business_impact_priority", nullable = false)
    private BusinessImpactPriority businessImpactPriority;

    @Column(name = "application_source_fe")
    private String applicationSourceFe;

    @Column(name = "application_source_be")
    private String applicationSourceBe;

    @Column(name = "ip_database")
    private String ipDatabase;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(
            name = "webapp_pic_developer",
            joinColumns = @JoinColumn(name = "id_webapp"),
            inverseJoinColumns = @JoinColumn(name = "id_pic_developer")
    )
    private List<PICDeveloperEntity> picDeveloper;

    @ManyToMany
    @JoinTable(
            name = "webapp_pic_analyst",
            joinColumns = @JoinColumn(name = "id_webapp"),
            inverseJoinColumns = @JoinColumn(name = "id_pic_analyst")
    )
    private List<PICAnalystEntity> picAnalyst;


    @ManyToMany
    @JoinTable(
            name = "webapp_mapping_function",
            joinColumns = @JoinColumn(name = "id_webapp"),
            inverseJoinColumns = @JoinColumn(name = "id_mapping_function")
    )
    private List<MappingFunctionEntity> mappingFunction;

    @ManyToMany
    @JoinTable(
            name = "webapp_front_end",
            joinColumns = @JoinColumn(name = "id_webapp"),
            inverseJoinColumns = @JoinColumn(name = "id_frontend")
    )
    private List<FrontEndEntity> frontEnd;

    @ManyToMany
    @JoinTable(
            name = "webapp_back_end",
            joinColumns = @JoinColumn(name = "id_webapp"),
            inverseJoinColumns = @JoinColumn(name = "id_backend")
    )
    private List<BackEndEntity> backEnd;

    @ManyToMany
    @JoinTable(
            name = "webapp_web_server",
            joinColumns = @JoinColumn(name = "id_webapp"),
            inverseJoinColumns = @JoinColumn(name = "id_web_server")
    )
    private List<WebServerEntity> webServer;

    @ManyToOne
    @JoinColumn(name = "id_sda_hosting")
    @JsonManagedReference
    private SDAHostingEntity sdaHosting;

    @OneToMany(mappedBy = "webAppEntity", fetch = FetchType.LAZY, cascade = jakarta.persistence.CascadeType.ALL)
    private List<DocumentUploadEntity> documentUploadList;

    @Cascade(CascadeType.ALL)
    @JsonManagedReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "webAppEntity", fetch = FetchType.LAZY)
    private List<FeedbackEntity> feedbackList;

}