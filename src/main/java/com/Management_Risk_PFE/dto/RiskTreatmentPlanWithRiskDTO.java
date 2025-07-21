package com.Management_Risk_PFE.dto;

import com.Management_Risk_PFE.enums.AssetType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RiskTreatmentPlanWithRiskDTO {
    private Long id;
    private String controlTarget;
    private String type;
    private String keyChallenges;
    private String priority;
    private String timeRequired;
    private LocalDate startDate;
    private LocalDate targetCompletionDate;
    private String status;
    private String delayJustification;
    private String acceptanceReasons;

    // Champs du Risk
    private String correspondingIsoControls;
    private String threatIdentification;
    private String vulnerabilityIdentification;
    private String incidentScenario;
    private String revisedProbabilityOfOccurrence;
    private String revisedLevelOfConsequences;
    private String suggestedControls;
    private String residualRisk;
    private String finalManagementDecision;
    private String riskOwner; // username
    private AssetType relatedAsset;
}
