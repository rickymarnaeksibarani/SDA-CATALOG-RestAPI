package sda.catalogue.sdacataloguerestapi.seeder;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import sda.catalogue.sdacataloguerestapi.core.enums.MasterDataStatus;
import sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Entities.TypeDatabaseEntity;
import sda.catalogue.sdacataloguerestapi.modules.TypeDatabase.Repositories.TypeDatabaseRepository;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class TypeOfDbSeeder implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(TypeOfDbSeeder.class);
    @Autowired
    private TypeDatabaseRepository typeDatabaseRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(args.getOptionValues("seeder") != null){
            List<String> seeder = Arrays.asList(args.getOptionValues("seeder").get(0).split(","));
            if(seeder.contains("typeOfDb")) {
                typeOfDb();
                log.info("Success run Type of DB seeder");
            }
        }else{
            log.info("TOD seeder skipped");
        }
    }

    @Transactional
    protected void typeOfDb(){
        TypeDatabaseEntity typeDatabaseEntity = new TypeDatabaseEntity();
        typeDatabaseEntity.setDbStatus(MasterDataStatus.ACTIVE);
        typeDatabaseEntity.setTypeDatabase("PostgreSQL");
        typeDatabaseRepository.save(typeDatabaseEntity);
    }
}
