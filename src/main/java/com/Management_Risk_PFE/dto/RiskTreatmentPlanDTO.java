package com.Management_Risk_PFE.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Setter
@Getter
public class RiskTreatmentPlanDTO {

    private Long id;

    private Long riskId;

    private String controlTarget; // Control should be Implemented on/for

    private String type;

    private String keyChallenges;

    private String priority;

    private String timeRequired;

    private LocalDate startDate;

    private LocalDate targetCompletionDate;

    private String status;

    private String delayJustification;

    private String acceptanceReasons;

    // Champs Risk DÉDOUBLÉS (pour import/export direct)
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
