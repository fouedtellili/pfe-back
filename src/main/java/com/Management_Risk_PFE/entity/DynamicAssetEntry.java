package com.Management_Risk_PFE.entity;

import com.Management_Risk_PFE.enums.AssetType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class DynamicAssetEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "asset_type", length = 30)
    private AssetType assetType; // Enum et non plus String

    @Lob
    @Column(columnDefinition = "TEXT")
    private String data;
}
