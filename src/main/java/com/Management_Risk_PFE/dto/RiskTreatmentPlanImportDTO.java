package com.Management_Risk_PFE.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class RiskTreatmentPlanImportDTO {
    // Champs RTP
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
    // Champs Risk
    private String relatedAsset;
    private String correspondingIsoControls;
    private String threatIdentification;
    private String vulnerabilityIdentification;
    private String incidentScenario;
    private String riskOwner;
    private String suggestedControls;
    private String revisedProbabilityOfOccurrence;
    private String revisedLevelOfConsequences;
    private String residualRisk;
    private String finalManagementDecision;
}
