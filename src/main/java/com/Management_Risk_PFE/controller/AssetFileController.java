package com.Management_Risk_PFE.controller;

import com.Management_Risk_PFE.entity.AssetFile;
import com.Management_Risk_PFE.entity.DynamicAssetEntry;
import com.Management_Risk_PFE.enums.AssetType;
import com.Management_Risk_PFE.repository.DynamicAssetEntryRepository;
import com.Management_Risk_PFE.services.auth.AssetFileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/asset-files")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AssetFileController {

    private final AssetFileService assetFileService;
    private final DynamicAssetEntryRepository dynamicAssetEntryRepository;

    @PostMapping("/upload/{assetType}")
    public ResponseEntity<?> uploadFile(@PathVariable AssetType assetType,
                                        @RequestParam("file") MultipartFile file) {
        assetFileService.storeFile(assetType, file);
        return ResponseEntity.ok("File uploaded successfully");
    }

    @GetMapping("/{assetType}")
    public ResponseEntity<List<AssetFile>> getFilesByType(@PathVariable AssetType assetType) {
        return ResponseEntity.ok(assetFileService.getFilesByAssetType(assetType));
    }

    @GetMapping("/download/{assetType}/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable AssetType assetType,
            @PathVariable String fileName) {

        Resource file = assetFileService.loadFileAsResource(assetType, fileName);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @GetMapping("/entries/{type}")
    public List<DynamicAssetEntry> getEntriesByType(@PathVariable String type) {
        AssetType assetType = AssetType.valueOf(type.toUpperCase());
        return dynamicAssetEntryRepository.findByAssetType(assetType);
    }

    // 📝 Sauvegarde d'une entrée dynamique
    @PostMapping("/entries/{type}")
    public ResponseEntity<?> saveEntry(@PathVariable String type, @RequestBody Map<String, Object> data) {
        try {
            AssetType assetType = AssetType.valueOf(type.toUpperCase());
            DynamicAssetEntry entry = new DynamicAssetEntry();
            entry.setAssetType(assetType);
            entry.setData(new ObjectMapper().writeValueAsString(data));
            return ResponseEntity.ok(dynamicAssetEntryRepository.save(entry));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid asset type: " + type);
        } catch (JsonProcessingException e) {
            return ResponseEntity.internalServerError().body("Error processing JSON");
        }
    }
}
