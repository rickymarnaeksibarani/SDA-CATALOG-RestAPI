package sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Entities.BackEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.DocumentUpload.Entities.DocumentUploadEntity;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Entities.FrontEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Entities.MappingFunctionEntity;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Entities.PICDeveloperEntity;
import sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Entities.SDAHostingEntity;
import sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Entities.TypeDatabaseEntity;
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
public class WebAppEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_webapp")
    private long idWebapp;

    @UuidGenerator
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(name = "application_name")
    private String applicationName;

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

    @Column(name = "link_ios")
    private String linkIOS;

    @Column(name = "link_android")
    private String linkAndroid;

    @Column(name = "file_manifest")
    private String fileManifest;

    @Column(name = "file_ipa")
    private String fileIpa;

    @Column(name = "file_android")
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

    @OneToMany(mappedBy = "webAppEntity", cascade = CascadeType.ALL)
    private List<DocumentUploadEntity> documentUploadList;

    @OneToMany(mappedBy = "webAppEntity", cascade = CascadeType.ALL)
    private List<VersioningApplicationEntity> versioningApplicationList;

    @OneToMany(mappedBy = "webAppEntity", cascade = CascadeType.ALL)
    private List<DatabaseEntity> databaseList;

    @ManyToOne
    @JoinColumn(name = "id_sda_hosting")
    @JsonIgnore
    private SDAHostingEntity sdaHostingEntity;
}
