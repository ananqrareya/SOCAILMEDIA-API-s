package com.social.media.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {
    private final Path rootLocations= Paths.get("uploads");
    public FileStorageService(){
        try {
            Files.createDirectories(rootLocations);
        }catch (IOException e){
            throw  new RuntimeException("Could not initialize storage",e);
        }
    }
    public String store(MultipartFile file) {
        try {
            String filename = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
            Files.copy(file.getInputStream(), this.rootLocations.resolve(filename));
            return filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }
    public Path load(String filename) {
        return rootLocations.resolve(filename);
    }

}
