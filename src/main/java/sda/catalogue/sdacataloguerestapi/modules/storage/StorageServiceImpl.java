package sda.catalogue.sdacataloguerestapi.modules.storage;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class StorageServiceImpl implements StorageService{
    @Override
    public void init() {

    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public Path load(String path) {
        return Paths.get(path);
    }

    @Override
    public Resource loadAsResource(String path) {
        try {
            Path file = load(path);
            Resource urlResource = new UrlResource(file.toUri());

            if (urlResource.exists() || urlResource.isReadable()) return urlResource;
            else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not read file");
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not read file: " + e);
        }
    }

    @Override
    public void deleteAll() {

    }
}
