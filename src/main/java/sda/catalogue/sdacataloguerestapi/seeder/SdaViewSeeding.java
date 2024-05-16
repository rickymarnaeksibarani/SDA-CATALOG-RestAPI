package sda.catalogue.sdacataloguerestapi.seeder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class SdaViewSeeding implements CommandLineRunner {
    public static final String VIEW_INIT_FILE = "db/sda-catalog_create-view-union-all-sda.sql";
    private static final Logger log = LoggerFactory.getLogger(SdaViewSeeding.class);
    @Autowired
    private DataSource dataSource;

    @Override
    public void run(String... args) throws Exception {
        createSdaView();
    }

    private void createSdaView() {
        boolean IGNORE_FAILED_DROP = true;
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, IGNORE_FAILED_DROP, "UTF-8", new ClassPathResource(VIEW_INIT_FILE));
        resourceDatabasePopulator.execute(dataSource);
    }
}
