package sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.WebAppEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Immutable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "tb_pic_developer")
public class PICDeveloperEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_pic_developer")
    private long idPicDeveloper;

    @UuidGenerator
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(name = "personal_number")
    private String personalNumber;

    @Column(name = "personal_name")
    private String personalName;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
