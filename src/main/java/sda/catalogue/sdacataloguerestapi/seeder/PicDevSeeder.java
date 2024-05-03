package sda.catalogue.sdacataloguerestapi.seeder;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Entities.PICDeveloperEntity;
import sda.catalogue.sdacataloguerestapi.modules.PICDeveloper.Repositories.PICDeveloperRepository;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class PicDevSeeder implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(PicDevSeeder.class);
    @Autowired
    private PICDeveloperRepository picDeveloperRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(args.getOptionValues("seeder") != null){
            List<String> seeder = Arrays.asList(args.getOptionValues("seeder").get(0).split(","));
            if(seeder.contains("picDev")) {
                seedPicDev();
                log.info("Success run PIC Dev seeder");
            }
        }else{
            log.info("PIC Dev seeder skipped");
        }
    }

    @Transactional
    protected void seedPicDev(){
        PICDeveloperEntity picDeveloperEntity = new PICDeveloperEntity();
        picDeveloperEntity.setPersonalName("Yonathan Simbolon");
        picDeveloperEntity.setPersonalNumber("782404");
        picDeveloperRepository.save(picDeveloperEntity);
    }
}
