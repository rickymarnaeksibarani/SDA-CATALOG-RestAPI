package sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import sda.catalogue.sdacataloguerestapi.core.enums.SapIntegration;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Entities.BackEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.DocumentUpload.Entities.DocumentUploadEntity;
import sda.catalogue.sdacataloguerestapi.modules.Feedback.Entities.FeedbackEntity;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Entities.FrontEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Entities.MappingFunctionEntity;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Entities.PICDeveloperEntity;
import sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Entities.SDAHostingEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebServer.Entities.WebServerEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Immutable
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
    private long idWebapp;

    @UuidGenerator
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(name = "application_name", nullable = false)
    private String applicationName;

    @Column(name = "pmo_number")
    private String pmoNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "sap_integration", nullable = false)
    private SapIntegration sapIntegration;

    @Column(name = "category_app")
    private String categoryApp;

    @Column(name = "description")
    private String description;

    @Column(name = "function_application")
    private String functionApplication;

    @Column(name = "address")
    private String address;

    @Column(name = "business_impact_priority")
    private String businessImpactPriority;

    @Column(name = "status")
    private String status;


    private String linkIOS;
    private String linkAndroid;
    private String fileManifest;
    private String fileIpa;
    private String fileAndroid;

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
    private List<PICDeveloperEntity> picDeveloperList;

    @ManyToMany
    @JoinTable(
            name = "webapp_mapping_function",
            joinColumns = @JoinColumn(name = "id_webapp"),
            inverseJoinColumns = @JoinColumn(name = "id_mapping_function")
    )
    private List<MappingFunctionEntity> mappingFunctionList;

    @ManyToMany
    @JoinTable(
            name = "webapp_front_end",
            joinColumns = @JoinColumn(name = "id_webapp"),
            inverseJoinColumns = @JoinColumn(name = "id_frontend")
    )
    private List<FrontEndEntity> frontEndList;

    @ManyToMany
    @JoinTable(
            name = "webapp_back_end",
            joinColumns = @JoinColumn(name = "id_webapp"),
            inverseJoinColumns = @JoinColumn(name = "id_backend")
    )
    private List<BackEndEntity> backEndList;

    @ManyToMany
    @JoinTable(
            name = "webapp_web_server",
            joinColumns = @JoinColumn(name = "id_webapp"),
            inverseJoinColumns = @JoinColumn(name = "id_web_server")
    )
    private List<WebServerEntity> webServerList;

    @ManyToOne
    @JoinColumn(name = "id_sda_hosting")
    @JsonIgnoreProperties("sdaHostingEntities")
    private SDAHostingEntity sdaHostingEntity;

    @OneToMany(mappedBy = "webAppEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DocumentUploadEntity> documentUploadList;

    @OneToMany(mappedBy = "webAppEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VersioningApplicationEntity> versioningApplicationList;

    @OneToMany(mappedBy = "webAppEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApiEntity> apiList;

    @OneToMany(mappedBy = "webAppEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DatabaseEntity> databaseList;

    @OneToMany(mappedBy = "webAppEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedbackEntity> feedbackList;
}