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
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Entities.FrontEndEntity;
import sda.catalogue.sdacataloguerestapi.modules.FrontEnd.Repositories.FrontEndRepository;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class FrontEndSeeder implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(FrontEndSeeder.class);
    @Autowired
    private FrontEndRepository frontEndRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(args.getOptionValues("seeder") != null){
            List<String> seeder = Arrays.asList(args.getOptionValues("seeder").get(0).split(","));
            if(seeder.contains("frontend")) {
                seedFrontEnd();
                log.info("Success run Front end seeder");
            }
        }else{
            log.info("Front End seeder skipped");
        }
    }

    @Transactional
    protected void seedFrontEnd(){
        FrontEndEntity frontEndEntity = new FrontEndEntity();
        frontEndEntity.setFeStatus(MasterDataStatus.ACTIVE);
        frontEndEntity.setFrontEnd("Angular");
        frontEndRepository.save(frontEndEntity);
    }
}
