package sda.catalogue.sdacataloguerestapi.seeder;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Entities.SDAHostingEntity;
import sda.catalogue.sdacataloguerestapi.modules.SDAHosting.Repositories.SDAHostingRepository;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SdaHostingSeeder implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(SdaHostingSeeder.class);
    @Autowired
    private SDAHostingRepository sdaHostingRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(args.getOptionValues("seeder") != null){
            List<String> seeder = Arrays.asList(args.getOptionValues("seeder").get(0).split(","));
            if(seeder.contains("sdaHosting")) {
                seedWebservice();
                log.info("Success run SDA Hosting seeder");
            }
        }else{
            log.info("SDA Hosting seeder skipped");
        }
    }

    @Transactional
    protected void seedWebservice(){
        SDAHostingEntity sdaHostingEntity = new SDAHostingEntity();
        sdaHostingEntity.setSdaHosting("AWS");
        sdaHostingRepository.save(sdaHostingEntity);
    }
}
