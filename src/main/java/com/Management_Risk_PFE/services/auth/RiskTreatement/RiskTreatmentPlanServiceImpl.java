package com.Management_Risk_PFE.services.auth.RiskTreatement;

import com.Management_Risk_PFE.dto.RiskTreatmentPlanDTO;
import com.Management_Risk_PFE.dto.RiskTreatmentPlanWithRiskDTO;
import com.Management_Risk_PFE.entity.Risk;
import com.Management_Risk_PFE.entity.RiskTreatmentPlan;
import com.Management_Risk_PFE.enums.AssetType;
import com.Management_Risk_PFE.repository.RiskRepository;
import com.Management_Risk_PFE.repository.RiskTreatmentPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.Management_Risk_PFE.dto.RiskTreatmentPlanImportDTO;
import java.util.Optional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RiskTreatmentPlanServiceImpl implements RiskTreatmentPlanService {

    private final RiskTreatmentPlanRepository riskTreatmentPlanRepository;
    private final RiskRepository riskRepository; // <-- AJOUTÉ

    @Override
    public RiskTreatmentPlanDTO createRiskTreatmentPlan(RiskTreatmentPlanDTO dto) {
        RiskTreatmentPlan entity = mapToEntity(dto);
        entity = riskTreatmentPlanRepository.save(entity);
        return mapToDTO(entity);
    }

    @Override
    public RiskTreatmentPlanDTO getRiskTreatmentPlanById(Long id) {
        RiskTreatmentPlan entity = riskTreatmentPlanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RiskTreatmentPlan not found"));
        return mapToDTO(entity);
    }

    @Override
    public List<RiskTreatmentPlanDTO> getAllRiskTreatmentPlans() {
        return riskTreatmentPlanRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RiskTreatmentPlanDTO updateRiskTreatmentPlan(Long id, RiskTreatmentPlanDTO dto) {
        RiskTreatmentPlan entity = riskTreatmentPlanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RiskTreatmentPlan not found"));

        entity.setControlTarget(dto.getControlTarget());
        entity.setType(dto.getType());
        entity.setKeyChallenges(dto.getKeyChallenges());
        entity.setPriority(dto.getPriority());
        entity.setTimeRequired(dto.getTimeRequired());
        entity.setStartDate(dto.getStartDate());
        entity.setTargetCompletionDate(dto.getTargetCompletionDate());
        entity.setStatus(dto.getStatus());
        entity.setDelayJustification(dto.getDelayJustification());
        entity.setAcceptanceReasons(dto.getAcceptanceReasons());

        entity = riskTreatmentPlanRepository.save(entity);
        return mapToDTO(entity);
    }

    @Override
    public void deleteRiskTreatmentPlan(Long id) {
        riskTreatmentPlanRepository.deleteById(id);
    }


    // Mappers
    private RiskTreatmentPlanDTO mapToDTO(RiskTreatmentPlan entity) {
        RiskTreatmentPlanDTO dto = new RiskTreatmentPlanDTO();
        dto.setId(entity.getId());
        dto.setControlTarget(entity.getControlTarget());
        dto.setType(entity.getType());
        dto.setKeyChallenges(entity.getKeyChallenges());
        dto.setPriority(entity.getPriority());
        dto.setTimeRequired(entity.getTimeRequired());
        dto.setStartDate(entity.getStartDate());
        dto.setTargetCompletionDate(entity.getTargetCompletionDate());
        dto.setStatus(entity.getStatus());
        dto.setDelayJustification(entity.getDelayJustification());
        dto.setAcceptanceReasons(entity.getAcceptanceReasons());

        // Champs Risk DÉDOUBLÉS
        dto.setRelatedAsset(entity.getRelatedAsset());
        dto.setCorrespondingIsoControls(entity.getCorrespondingIsoControls());
        dto.setThreatIdentification(entity.getThreatIdentification());
        dto.setVulnerabilityIdentification(entity.getVulnerabilityIdentification());
        dto.setIncidentScenario(entity.getIncidentScenario());
        dto.setRiskOwner(entity.getRiskOwner());
        dto.setSuggestedControls(entity.getSuggestedControls());
        dto.setRevisedProbabilityOfOccurrence(entity.getRevisedProbabilityOfOccurrence());
        dto.setRevisedLevelOfConsequences(entity.getRevisedLevelOfConsequences());
        dto.setResidualRisk(entity.getResidualRisk());
        dto.setFinalManagementDecision(entity.getFinalManagementDecision());

        if (entity.getRisk() != null) {
            dto.setRiskId(entity.getRisk().getId());
        }
        return dto;
    }

    private RiskTreatmentPlan mapToEntity(RiskTreatmentPlanDTO dto) {
        RiskTreatmentPlan entity = new RiskTreatmentPlan();
        entity.setControlTarget(dto.getControlTarget());
        entity.setType(dto.getType());
        entity.setKeyChallenges(dto.getKeyChallenges());
        entity.setPriority(dto.getPriority());
        entity.setTimeRequired(dto.getTimeRequired());
        entity.setStartDate(dto.getStartDate());
        entity.setTargetCompletionDate(dto.getTargetCompletionDate());
        entity.setStatus(dto.getStatus());
        entity.setDelayJustification(dto.getDelayJustification());
        entity.setAcceptanceReasons(dto.getAcceptanceReasons());

        // Champs Risk DÉDOUBLÉS
        entity.setRelatedAsset(dto.getRelatedAsset());
        entity.setCorrespondingIsoControls(dto.getCorrespondingIsoControls());
        entity.setThreatIdentification(dto.getThreatIdentification());
        entity.setVulnerabilityIdentification(dto.getVulnerabilityIdentification());
        entity.setIncidentScenario(dto.getIncidentScenario());
        entity.setRiskOwner(dto.getRiskOwner());
        entity.setSuggestedControls(dto.getSuggestedControls());
        entity.setRevisedProbabilityOfOccurrence(dto.getRevisedProbabilityOfOccurrence());
        entity.setRevisedLevelOfConsequences(dto.getRevisedLevelOfConsequences());
        entity.setResidualRisk(dto.getResidualRisk());
        entity.setFinalManagementDecision(dto.getFinalManagementDecision());

        // Optionnel : rattacher à Risk d'origine si fourni
        if (dto.getRiskId() != null) {
            Risk risk = riskRepository.findById(dto.getRiskId())
                    .orElseThrow(() -> new RuntimeException("Risk not found with id " + dto.getRiskId()));
            entity.setRisk(risk);
        }
        return entity;
    }

    @Override
    public List<RiskTreatmentPlanWithRiskDTO> getAllWithRiskDetails() {
        List<RiskTreatmentPlan> plans = riskTreatmentPlanRepository.findAll();
        return plans.stream()
                .map(this::mapToRiskTreatmentPlanWithRiskDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RiskTreatmentPlanDTO> importRiskTreatmentPlans(List<RiskTreatmentPlanDTO> rtps) {
        List<RiskTreatmentPlan> entities = rtps.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
        List<RiskTreatmentPlan> saved = riskTreatmentPlanRepository.saveAll(entities);
        return saved.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private RiskTreatmentPlanWithRiskDTO mapToRiskTreatmentPlanWithRiskDTO(RiskTreatmentPlan plan) {
        RiskTreatmentPlanWithRiskDTO dto = new RiskTreatmentPlanWithRiskDTO();

        dto.setId(plan.getId());
        dto.setControlTarget(plan.getControlTarget());
        dto.setType(plan.getType());
        dto.setKeyChallenges(plan.getKeyChallenges());
        dto.setPriority(plan.getPriority());
        dto.setTimeRequired(plan.getTimeRequired());
        dto.setStartDate(plan.getStartDate());
        dto.setTargetCompletionDate(plan.getTargetCompletionDate());
        dto.setStatus(plan.getStatus());
        dto.setDelayJustification(plan.getDelayJustification());
        dto.setAcceptanceReasons(plan.getAcceptanceReasons());

        if (plan.getRisk() != null) {
            Risk risk = plan.getRisk();
            dto.setCorrespondingIsoControls(risk.getCorrespondingIsoControls());
            dto.setThreatIdentification(risk.getThreatIdentification());
            dto.setVulnerabilityIdentification(risk.getVulnerabilityIdentification());
            dto.setIncidentScenario(risk.getIncidentScenario());
            dto.setRevisedProbabilityOfOccurrence(risk.getRevisedProbabilityOfOccurrence());
            dto.setRevisedLevelOfConsequences(risk.getRevisedLevelOfConsequences());
            dto.setResidualRisk(risk.getResidualRisk());
            dto.setFinalManagementDecision(risk.getFinalManagementDecision());
            dto.setRelatedAsset(risk.getRelatedAsset());
            dto.setSuggestedControls(risk.getSuggestedControls());

            if (risk.getRiskOwner() != null) {
                dto.setRiskOwner(risk.getRiskOwner().getName());
            } else {
                dto.setRiskOwner(null);
            }
        } else {
            // Champs dupliqués dans l'entité RTP (issus de l'import Excel)
            dto.setCorrespondingIsoControls(plan.getCorrespondingIsoControls());
            dto.setThreatIdentification(plan.getThreatIdentification());
            dto.setVulnerabilityIdentification(plan.getVulnerabilityIdentification());
            dto.setIncidentScenario(plan.getIncidentScenario());
            dto.setRevisedProbabilityOfOccurrence(plan.getRevisedProbabilityOfOccurrence());
            dto.setRevisedLevelOfConsequences(plan.getRevisedLevelOfConsequences());
            dto.setResidualRisk(plan.getResidualRisk());
            dto.setFinalManagementDecision(plan.getFinalManagementDecision());
            dto.setSuggestedControls(plan.getSuggestedControls());
            dto.setRiskOwner(plan.getRiskOwner());

            // Conversion du String vers AssetType (enum)
            if (plan.getRelatedAsset() != null) {
                try {
                    dto.setRelatedAsset(AssetType.valueOf(plan.getRelatedAsset().toUpperCase()));
                } catch (IllegalArgumentException ex) {
                    // Si la valeur ne correspond pas à un enum, mettre null ou une valeur par défaut
                    dto.setRelatedAsset(null); // ou AssetType.OTHER si tu as une valeur de secours
                }
            } else {
                dto.setRelatedAsset(null);
            }
        }

        return dto;
    }


}
