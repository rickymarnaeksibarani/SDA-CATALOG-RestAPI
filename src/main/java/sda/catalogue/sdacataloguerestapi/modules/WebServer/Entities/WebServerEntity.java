package sda.catalogue.sdacataloguerestapi.modules.WebServer.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import sda.catalogue.sdacataloguerestapi.core.enums.MasterDataStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Immutable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "tb_web_server")
public class WebServerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_web_server")
    private long idWebServer;

    @UuidGenerator
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(name = "web_server")
    private String webServer;

//    @Column(name = "web_service_status", nullable = false)
//    private MasterDataStatus webServiceStatus;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
