package com.alltruth.api.service;

import com.alltruth.api.config.common.exceptions.file.StorageException;
import com.alltruth.api.config.common.exceptions.file.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class FileUploadService {
    // 현재 back 폴더 기준임. 스프링을 실행시킨 폴더의 기준
    private final Path rootLocation = Paths.get("upload");



    public void store(MultipartFile file) {
        try {
            if (!Files.exists(rootLocation.toAbsolutePath().normalize())) {
                System.out.println("폴더가 없어용");
                Files.createDirectories(rootLocation.toAbsolutePath().normalize());
            }
            if (file.isEmpty()) {
                throw new Exception("Failed to store empty file.");
            }
            Path destinationFile = this.rootLocation.resolve(
                            Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();
            System.out.println("destinationFile::: " + destinationFile);
            System.out.println(destinationFile.getParent());
            System.out.println(this.rootLocation.toAbsolutePath().normalize());
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new Exception(
                        "경로가 다름.");
            }
            System.out.println(file.getOriginalFilename() + "sdfsffds");
            file.transferTo(destinationFile);

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new StorageException("Failed to store file.");
        }
    }


    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }


    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }


    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            System.out.println("filename::: " + filename);
            System.out.println(resource);

            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }


    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }


    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}

