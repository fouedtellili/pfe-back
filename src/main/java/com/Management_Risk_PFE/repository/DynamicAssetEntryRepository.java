package com.Management_Risk_PFE.repository;

import com.Management_Risk_PFE.entity.DynamicAssetEntry;
import com.Management_Risk_PFE.enums.AssetType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DynamicAssetEntryRepository extends JpaRepository<DynamicAssetEntry, Long> {
    List<DynamicAssetEntry> findByAssetType(AssetType assetType);
}
