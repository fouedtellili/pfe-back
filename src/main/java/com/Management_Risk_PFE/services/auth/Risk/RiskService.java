package com.Management_Risk_PFE.services.auth.Risk;

import com.Management_Risk_PFE.dto.RiskDto;

import java.util.List;

public interface RiskService {
    RiskDto createRisk(RiskDto riskDTO);
    RiskDto getRiskById(Long id);
    List<RiskDto> getAllRisks();
    RiskDto updateRisk(Long id, RiskDto riskDTO);
    void deleteRisk(Long id);

    List<RiskDto> importRisks(List<RiskDto> risks);
}
