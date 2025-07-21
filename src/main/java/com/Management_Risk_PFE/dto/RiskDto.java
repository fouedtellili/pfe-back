package com.Management_Risk_PFE.dto;

import com.Management_Risk_PFE.enums.AssetType;
import lombok.Data;

@Data
public class RiskDto {
    private Long id;
    private AssetType relatedAsset;
    private String subAsset;
    private Long assetOwnerId;
    private String confidentiality;
    private String integrity;
    private String availability;
    private String assetRating;
    private String threatIdentification;
    private String vulnerabilityIdentification;
    private String incidentScenario;
    private String probabilityOfOccurrence;
    private String riskLevel;
    private String consequences;
    private String levelOfConsequences;
    private Long riskOwnerId;
    private String riskManagementDecision;
    private String suggestedControls;
    private String correspondingIsoControls;
    private String revisedProbabilityOfOccurrence;
    private String revisedLevelOfConsequences;
    private String residualRisk;
    private String finalManagementDecision;
}
