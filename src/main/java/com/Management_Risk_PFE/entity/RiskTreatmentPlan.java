package com.Management_Risk_PFE.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class RiskTreatmentPlan {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "risk_id")
    private Risk risk;

    @Column(name = "control_target")
    private String controlTarget; // Control should be Implemented on/for

    @Column(columnDefinition = "LONGTEXT")
    private String type;

    @Column(name = "key_challenges")
    private String keyChallenges;

    private String priority;

    @Column(name = "time_required")
    private String timeRequired;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "target_completion_date")
    private LocalDate targetCompletionDate;

    private String status;

    @Column(name = "delay_justification")
    private String delayJustification;

    @Column(name = "acceptance_reasons")
    private String acceptanceReasons;

    // Champs Risk DÉDOUBLÉS (pour import/export direct)
    private String relatedAsset;
    private String correspondingIsoControls;
    @Column(columnDefinition = "TEXT")
    private String threatIdentification;
    @Column(columnDefinition = "TEXT")
    private String vulnerabilityIdentification;
    @Column(columnDefinition = "TEXT")
    private String incidentScenario;
    private String riskOwner;

    @Column(columnDefinition = "LONGTEXT")
    private String suggestedControls;

    private String revisedProbabilityOfOccurrence;
    private String revisedLevelOfConsequences;
    private String residualRisk;
    private String finalManagementDecision;
}
