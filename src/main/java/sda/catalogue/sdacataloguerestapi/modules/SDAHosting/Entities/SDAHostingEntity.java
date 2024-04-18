package sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Entities;

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
@Table(name = "tb_sda_hosting")
public class SDAHostingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_sda_hosting")
    private Long idSDAHosting;

    @UuidGenerator
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(name = "sda_hosting")
    private String sdaHosting;

//    @Column(name = "hosting_status", nullable = false)
//    private MasterDataStatus hostingStatus;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public class FormattedSDAHostingEntity{
        private long idSDAHosting;
        private String sdaHosting;

        public FormattedSDAHostingEntity(long idSDAHosting, String sdaHosting){
            this.idSDAHosting = idSDAHosting;
            this.sdaHosting = sdaHosting;
        }
    }


}
