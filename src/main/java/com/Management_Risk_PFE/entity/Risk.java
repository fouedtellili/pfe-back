package com.Management_Risk_PFE.entity;

import com.Management_Risk_PFE.enums.AssetType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "risks")
public class Risk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Related Asset (relation avec enum AssetType)
    @Enumerated(EnumType.STRING)
    private AssetType relatedAsset;

    private String subAsset;

    // Asset Owner (relation avec User)
    @ManyToOne
    @JoinColumn(name = "asset_owner_id")
    private User assetOwner;

    // Asset Value
    private String confidentiality;
    private String integrity;
    private String availability;

    private String assetRating;

    // Threat and vulnerability
    private String threatIdentification;
    private String vulnerabilityIdentification;
    private String incidentScenario;

    private String probabilityOfOccurrence;
    private String riskLevel;
    private String consequences;
    private String levelOfConsequences;

    // Risk Owner
    @ManyToOne
    @JoinColumn(name = "risk_owner_id")
    private User riskOwner;

    private String riskManagementDecision;
    private String suggestedControls;
    private String correspondingIsoControls;

    // Risk re-calculation
    private String revisedProbabilityOfOccurrence;
    private String revisedLevelOfConsequences;
    private String residualRisk;
    private String finalManagementDecision;
}
