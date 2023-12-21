package sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Entities.DinasEntity;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Entities.MappingFunctionEntity;

import java.util.UUID;

@Repository
public interface DinasRepository extends JpaRepository<DinasEntity, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE DinasEntity w SET " +
            "w.dinas = :dinas " +
            "WHERE w.uuid = :uuid")
    int findByUuidAndUpdate(UUID uuid, String dinas);

    @Transactional
    void deleteAllByMappingFunctionEntity(MappingFunctionEntity mappingFunctionEntity);

    @Modifying
    @Transactional
    @Query("DELETE FROM DinasEntity w WHERE w.uuid = :uuid")
    int findUuidAndDelete(UUID uuid);
}
