package com.Management_Risk_PFE.services.auth.RiskTreatement;

import com.Management_Risk_PFE.dto.RiskTreatmentPlanDTO;
import com.Management_Risk_PFE.dto.RiskTreatmentPlanWithRiskDTO;

import java.util.List;

public interface RiskTreatmentPlanService {

    RiskTreatmentPlanDTO createRiskTreatmentPlan(RiskTreatmentPlanDTO dto);

    RiskTreatmentPlanDTO getRiskTreatmentPlanById(Long id);

    List<RiskTreatmentPlanDTO> getAllRiskTreatmentPlans();

    RiskTreatmentPlanDTO updateRiskTreatmentPlan(Long id, RiskTreatmentPlanDTO dto);

    void deleteRiskTreatmentPlan(Long id);

    // Nouvelle méthode pour récupérer les RTP avec les détails Risk associés
    List<RiskTreatmentPlanWithRiskDTO> getAllWithRiskDetails();

    List<RiskTreatmentPlanDTO> importRiskTreatmentPlans(List<RiskTreatmentPlanDTO> rtps);

}
