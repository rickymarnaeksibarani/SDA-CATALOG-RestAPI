package sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Immutable
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_webapp")
public class WebAppEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_webapp")
    private long idWebapp;

    @UuidGenerator
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @NotBlank(message = "application name is required!")
    @Column(name = "application_name", nullable = false)
    private String applicationName;

    @NotBlank(message = "description is required!")
    @Column(name = "description", nullable = false)
    private String description;

    @NotBlank(message = "function application is required!")
    @Column(name = "function_application", nullable = false)
    private String functionApplication;

    @NotBlank(message = "address is required!")
    @Column(name = "address", nullable = false)
    private String address;

    @NotBlank(message = "mapping function is required!")
    @NotBlank(message = "dinas is required!")
    @Column(name = "dinas", nullable = false)
    private String dinas;

    @NotBlank(message = "business impact priority is required!")
    @Column(name = "mapping_function", nullable = false)
    private String mappingFunction;


    @NotBlank(message = "business impact priority is required!")
    @Column(name = "business_impact_priority", nullable = false)
    private String businessImpactPriority;

    @NotBlank(message = "status is required!")
    @Column(name = "status", nullable = false)
    private String status;

    @NotBlank(message = "sda cloud is required!")
    @Column(name = "sda_cloud", nullable = false)
    private String sdaCloud;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
