package com.Management_Risk_PFE.services.auth;

import com.Management_Risk_PFE.entity.AssetFile;
import com.Management_Risk_PFE.enums.AssetType;
import com.Management_Risk_PFE.repository.AssetFileRepository;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Service
@RequiredArgsConstructor
public class AssetFileService {

    private final AssetFileRepository fileRepository;
    private final Path fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();

    private final Path rootLocation = Paths.get("uploads");

    public void storeFile(AssetType assetType, MultipartFile file) {
        try {
            Path assetDir = rootLocation.resolve(assetType.name().toLowerCase());
            Files.createDirectories(assetDir);

            Path destination = assetDir.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            AssetFile assetFile = new AssetFile();
            assetFile.setFileName(file.getOriginalFilename());
            assetFile.setFileUrl("/uploads/" + assetType.name().toLowerCase() + "/" + file.getOriginalFilename());
            assetFile.setAssetType(assetType);
            fileRepository.save(assetFile);

        } catch (IOException | java.io.IOException e) {
            throw new RuntimeException("Could not store file", e);
        }
    }

    public List<AssetFile> getFilesByAssetType(AssetType type) {
        return fileRepository.findByAssetType(type);
    }
    public Resource loadFileAsResource(AssetType assetType, String fileName) {
        try {
            // 🔁 On va chercher dans "uploads/ASSET_TYPE/filename"
            Path assetFolder = fileStorageLocation.resolve(assetType.name());
            Path filePath = assetFolder.resolve(fileName).normalize();

            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("File not found or not readable: " + fileName);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Could not load file: " + fileName, ex);
        }
    }
}
