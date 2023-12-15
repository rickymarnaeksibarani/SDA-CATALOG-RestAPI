package sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Entities.PICDeveloperEntity;

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


    @Column(name = "description")
    private String description;

    @Column(name = "function_application")
    private String functionApplication;

    @Column(name = "address")
    private String address;

    @Column(name = "dinas")
    private String dinas;

    @Column(name = "mapping_function")
    private String mappingFunction;

    @Column(name = "business_impact_priority")
    private String businessImpactPriority;

    @Column(name = "status")
    private String status;

    @Column(name = "sda_cloud")
    private String sdaCloud;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(
            name = "webapp_picdeveloper",
            joinColumns = @JoinColumn(name = "id_webapp"),
            inverseJoinColumns = @JoinColumn(name = "id_pic_developer")
    )
    private List<PICDeveloperEntity> picDeveloperList;
}
