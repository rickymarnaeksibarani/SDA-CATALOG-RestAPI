package sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;
import sda.catalogue.sdacataloguerestapi.core.enums.MasterDataStatus;
import sda.catalogue.sdacataloguerestapi.modules.WebApp.Entities.WebAppEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "master_sda_hosting")
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

    @Column(name = "sdaHosting_status")
    @Enumerated(EnumType.STRING)
    private MasterDataStatus sdaHostingStatus;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "id_webapp")
    @JsonBackReference
    private WebAppEntity webAppEntity;

    public class FormattedSDAHostingEntity{
        private long idSDAHosting;
        private String sdaHosting;

        public FormattedSDAHostingEntity(long idSDAHosting, String sdaHosting){
            this.idSDAHosting = idSDAHosting;
            this.sdaHosting = sdaHosting;
        }
    }


}
