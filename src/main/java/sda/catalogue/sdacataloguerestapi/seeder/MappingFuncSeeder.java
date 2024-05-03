package sda.catalogue.sdacataloguerestapi.seeder;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Entities.DinasEntity;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Entities.MappingFunctionEntity;
import sda.catalogue.sdacataloguerestapi.modules.MappingFunction.Repositories.MappingFunctionRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class MappingFuncSeeder implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(MappingFuncSeeder.class);
    @Autowired
    private MappingFunctionRepository mappingFunctionRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (args.getOptionValues("seeder") != null) {
            List<String> seeder = Arrays.asList(args.getOptionValues("seeder").get(0).split(","));

            if (seeder.contains("mapping")) {
                seedMapping();
                log.info("Success run mapping function seeder");
            }
        } else {
            log.info("Mapping function seeder skipped");
        }
    }

    @Transactional
    protected void seedMapping() {
        List<DinasEntity> dinasEntityList = new ArrayList<>();
        DinasEntity dinasEntity = new DinasEntity();
        dinasEntity.setDinas("TD");
        dinasEntityList.add(dinasEntity);
        dinasEntity.setDinas("TH");
        dinasEntityList.add(dinasEntity);

        MappingFunctionEntity mappingFunctionEntity = new MappingFunctionEntity();
        mappingFunctionEntity.setDinasEntityList(dinasEntityList);
        mappingFunctionEntity.setMappingFunction("Line Maintenance");
        dinasEntity.setMappingFunctionEntity(mappingFunctionEntity);
        mappingFunctionRepository.save(mappingFunctionEntity);
    }
}
