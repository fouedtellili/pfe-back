package com.Management_Risk_PFE.repository;

import com.Management_Risk_PFE.entity.AssetFile;
import com.Management_Risk_PFE.enums.AssetType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetFileRepository extends JpaRepository<AssetFile, Long> {
    List<AssetFile> findByAssetType(AssetType assetType);
}
