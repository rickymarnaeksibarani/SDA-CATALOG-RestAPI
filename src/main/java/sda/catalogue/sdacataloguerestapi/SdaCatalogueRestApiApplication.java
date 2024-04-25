package sda.catalogue.sdacataloguerestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaAuditing
@EnableCaching
public class SdaCatalogueRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SdaCatalogueRestApiApplication.class, args);
	}

}