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
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Entities.BackEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.BackEnd.Repositories.BackEndRepository;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class BackEndSeeder implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(BackEndSeeder.class);
    @Autowired
    private BackEndRepository backEndRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(args.getOptionValues("seeder") != null){
            List<String> seeder = Arrays.asList(args.getOptionValues("seeder").get(0).split(","));
            if(seeder.contains("backend")) {
                seedBackEnd();
                log.info("Success run Back end seeder");
            }
        }else{
            log.info("Back End seeder skipped");
        }
    }

    @Transactional
    protected void seedBackEnd(){
        BackEndEntity backEndEntity = new BackEndEntity();
        backEndEntity.setBeStatus(MasterDataStatus.ACTIVE);
        backEndEntity.setBackEnd("Spring Boot");
        backEndRepository.save(backEndEntity);
    }
}
