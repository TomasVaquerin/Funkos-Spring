package com.example.pruebaspringfunko.storage.services;

import com.example.pruebaspringfunko.storage.exceptions.StorageNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService{
    private static final Path root = Paths.get("funko-images");
    @Override
    public void init() {

    }

    @Override
    public String store(MultipartFile file) throws IOException {
        String unicFilename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path rootPath = load(unicFilename);
        Files.copy(file.getInputStream(), rootPath);

        return unicFilename;
    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public Path load(String filename) {
        return root.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) throws MalformedURLException {
        Path file = load(filename);
        Resource resource = new UrlResource(file.toUri());
        if (resource.exists() || resource.isReadable()){
            return resource;
        }else {
            throw new StorageNotFoundException("El archivo no se ha encontrado");
        }
    }

    @Override
    public void delete(String filename) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public String getUrl(String filename) {
        return null;
    }
}
