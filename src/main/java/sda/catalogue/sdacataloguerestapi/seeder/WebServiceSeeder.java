package sda.catalogue.sdacataloguerestapi.seeder;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import sda.catalogue.sdacataloguerestapi.modules.WebServer.Entities.WebServerEntity;
import sda.catalogue.sdacataloguerestapi.modules.WebServer.Repositories.WebServerRepository;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebServiceSeeder implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(WebServiceSeeder.class);
    @Autowired
    private WebServerRepository webServerRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(args.getOptionValues("seeder") != null){
            List<String> seeder = Arrays.asList(args.getOptionValues("seeder").get(0).split(","));
            if(seeder.contains("webservice")) {
                seedWebservice();
                log.info("Success run Web service seeder");
            }
        }else{
            log.info("Web service seeder skipped");
        }
    }

    @Transactional
    protected void seedWebservice(){
        WebServerEntity webServerEntity = new WebServerEntity();
        webServerEntity.setWebServer("Nginx");
        webServerRepository.save(webServerEntity);
    }
}
