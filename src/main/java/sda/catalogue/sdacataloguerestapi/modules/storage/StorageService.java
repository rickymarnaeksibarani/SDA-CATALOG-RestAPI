package sda.catalogue.sdacataloguerestapi.modules.storage;



import org.springframework.core.io.Resource;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {
    void init();
    Stream<Path> loadAll();
    Path load(String path);
    Resource loadAsResource(String path);
    void deleteAll();
}
