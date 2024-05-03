package sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import sda.catalogue.sdacataloguerestapi.core.enums.MasterDataStatus;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.DatabaseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "master_type_database")
public class TypeDatabaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_type_database")
    private long idTypeDatabase;

    @UuidGenerator
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(name = "type_database")
    private String typeDatabase;

    @Column(name = "db_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private MasterDataStatus dbStatus;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "typeDatabaseEntity")
    private List<DatabaseEntity> databaseEntities;
}
