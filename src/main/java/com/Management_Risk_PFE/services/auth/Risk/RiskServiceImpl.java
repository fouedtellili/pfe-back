package com.Management_Risk_PFE.services.auth.Risk;


import com.Management_Risk_PFE.dto.RiskDto;
import com.Management_Risk_PFE.entity.Risk;
import com.Management_Risk_PFE.repository.RiskRepository;
import com.Management_Risk_PFE.repository.UserRepository;
import com.Management_Risk_PFE.services.auth.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RiskServiceImpl implements RiskService{

    private final RiskRepository riskRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Override
    public RiskDto createRisk(RiskDto dto) {
        Risk risk = mapToEntity(dto);
        risk = riskRepository.save(risk);

        // Envoi d'un email texte au Asset Owner 1er mail
        if (risk.getAssetOwner() != null && risk.getAssetOwner().getEmail() != null) {
            String to = risk.getAssetOwner().getEmail();
            String name = risk.getAssetOwner().getName();


            String htmlMessage = """
                    <html>
                      <body>
                        <p>Hello %s,</p>

                        <p>A new risk has been identified for the asset you own. Below are the impact values:</p>

                        <p>
                          <strong>Asset Identification:</strong> %s<br/>
                          <strong>Confidentiality:</strong> %s<br/>
                          <strong>Integrity:</strong> %s<br/>
                          <strong>Availability:</strong> %s<br/>
                          <strong>Asset Rating:</strong> %s
                        </p>

                        <p><strong>Risk Rating Reference Table:</strong></p>
                        <table border="1" cellpadding="6" cellspacing="0" style="border-collapse: collapse;">
                          <tr>
                            <th>Rating</th>
                            <th>Level</th>
                            <th>Description</th>
                          </tr>
                          <tr><td>Negligible</td><td>1</td><td>Breach could result in little or no loss or injury</td></tr>
                          <tr><td>Low</td><td>2</td><td>Breach could result in minor loss or injury</td></tr>
                          <tr><td>Medium</td><td>3</td><td>Breach could result in serious loss or injury, and the business process could be negatively affected</td></tr>
                          <tr><td>High</td><td>4</td><td>Breach could result in very serious loss or injury, and the business process could fail</td></tr>
                          <tr><td>Very High</td><td>5</td><td>Breach could result in financial losses, or in exceptionally grave injury to individual or the organization and the business process will fail</td></tr>
                        </table>

                        <p style="color: red; font-weight: bold; font-size: 20px;">Approve them please,<br/>
                        Best regards,<br/>
                        Risk Management Team</p>
                      </body>
                    </html>
                    """.formatted(
                    name,
                    risk.getRelatedAsset(),
                    risk.getConfidentiality(),
                    risk.getIntegrity(),
                    risk.getAvailability(),
                    risk.getAssetRating()
            );


            // Envoi de l'email texte brut
            emailService.sendHtmlEmail(to, "New Risk Assessment for Your Asset", htmlMessage);
        }


        // 🔹 Envoi au Risk Owner 2eme mail
        if (risk.getRiskOwner() != null && risk.getRiskOwner().getEmail() != null) {
            String to = risk.getRiskOwner().getEmail();
            String name = risk.getRiskOwner().getName();

            String htmlMessage = """
                    <html>
                      <body>
                        <p>Hello %s,</p>

                        <p>You have been assigned a new risk. Below are the risk details:</p>

                        <p>
                          <strong>Risk ID:</strong> %s<br/>
                          <strong>Threat Identification:</strong> %s<br/>
                          <strong>Vulnerability Identification:</strong> %s<br/>
                          <strong>Incident Scenario:</strong> %s<br/>
                          <strong>Probability of Occurrence:</strong> %s<br/>
                          <strong>Consequences:</strong> %s<br/>
                          <strong>Risk Level:</strong> %s<br/>
                          <strong>Level of Consequences:</strong> %s
                        </p>

                        <p><strong>Risk Matrix:</strong></p>
                        <table border="1" cellpadding="6" cellspacing="0" style="border-collapse: collapse; text-align: center;">
                          <tr>
                            <th rowspan="2" style="background-color:#ccc;">Likelihood</th>
                            <th colspan="3" style="background-color:#ccc;">Consequences</th>
                          </tr>
                          <tr>
                            <th style="background-color:#eee;">Low</th>
                            <th style="background-color:#eee;">Moderate</th>
                            <th style="background-color:#eee;">Major</th>
                          </tr>
                          <tr>
                            <td style="background-color:#eee;">Rare</td>
                            <td style="background-color:#8FBC8F;">Low</td>
                            <td style="background-color:#FFD700;">Moderate</td>
                            <td style="background-color:#FFD700;">Moderate</td>
                          </tr>
                          <tr>
                            <td style="background-color:#eee;">Moderate</td>
                            <td style="background-color:#FFD700;">Moderate</td>
                            <td style="background-color:#FFD700;">Moderate</td>
                            <td style="background-color:#FF4500;">High</td>
                          </tr>
                          <tr>
                            <td style="background-color:#eee;">Almost Certain</td>
                            <td style="background-color:#FFD700;">Moderate</td>
                            <td style="background-color:#FF4500;">High</td>
                            <td style="background-color:#8B0000; color:white;">Extreme</td>
                          </tr>
                        </table>

                        <p>Thank you,<br/>
                        <p style="color: red; font-weight: bold; font-size: 20px;">Approve them please,<br/>
                        Risk Management Team</p>
                      </body>
                    </html>
                    """.formatted(
                    name,
                    risk.getId(),
                    risk.getThreatIdentification(),
                    risk.getVulnerabilityIdentification(),
                    risk.getIncidentScenario(),
                    risk.getProbabilityOfOccurrence(),
                    risk.getConsequences(),
                    risk.getRiskLevel(),
                    risk.getLevelOfConsequences()
            );

            emailService.sendHtmlEmail(to, "New Risk Assigned to You", htmlMessage);
        }
        // 🔹 Envoi au Risk Owner 3eme mail
        if (risk.getRiskOwner() != null && risk.getRiskOwner().getEmail() != null) {
            String to = risk.getRiskOwner().getEmail();
            String name = risk.getRiskOwner().getName();
        // 🔹 Envoi au Risk Owner : décision de traitement
        String decisionMessage = """
                <html>
                  <body>
                    <p>Hello %s,</p>

                    <p>Here are the treatment decisions proposed for the newly assigned risk:</p>

                    <p>
                      <strong>Risk Management Decision:</strong> %s<br/>
                      <strong>Suggested RTP:</strong> %s<br/>
                      <strong>Corresponding ISO Controls:</strong> %s
                    </p>

                    <p>Thank you,<br/>
                    <p style="color: red; font-weight: bold; font-size: 20px;">Approve them please,<br/>
                   Risk Management Team</p>
                  </body>
                </html>
                """.formatted(
                name,
                risk.getRiskManagementDecision(),
                risk.getSuggestedControls(),
                risk.getCorrespondingIsoControls()
        );



        emailService.sendHtmlEmail(to, "Risk Treatment Plan Proposed", decisionMessage);
    }
        // 🔹 Envoi au Risk Owner 4eme mail
        if (risk.getRiskOwner() != null && risk.getRiskOwner().getEmail() != null) {
            String to = risk.getRiskOwner().getEmail();
            String name = risk.getRiskOwner().getName();
            String decisionMessage = """
<html>
  <body>
    <p>Hello %s,</p>

    <p>Here are the treatment decisions proposed for the newly assigned risk:</p>

    <p>
      <strong>Risk Management Decision:</strong> %s<br/>
      <strong>Suggested RTP:</strong> %s<br/>
      <strong>Corresponding ISO Controls:</strong> %s
    </p>

    <p>Thank you,<br/>
    <p>Risk Management Team</p>
    <p style="color: red; font-weight: bold; font-size: 20px;">Approve them please,<br/>
  </body>
</html>
""".formatted(
                    name,
                    risk.getRiskManagementDecision(),
                    risk.getSuggestedControls(),
                    risk.getCorrespondingIsoControls()
            );

            emailService.sendHtmlEmail(to, "Risk Treatment Plan Proposed", decisionMessage);
        }

        return mapToDTO(risk);
    }

    @Override
    public RiskDto getRiskById(Long id) {
        Risk risk = riskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Risk not found"));
        return mapToDTO(risk);
    }

    @Override
    public List<RiskDto> getAllRisks() {
        return riskRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RiskDto updateRisk(Long id, RiskDto dto) {
        Risk risk = riskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Risk not found"));

        // Update fields
        risk.setRelatedAsset(dto.getRelatedAsset());
        risk.setSubAsset(dto.getSubAsset());
        risk.setAssetOwner(userRepository.findById(dto.getAssetOwnerId()).orElse(null));
        risk.setConfidentiality(dto.getConfidentiality());
        risk.setIntegrity(dto.getIntegrity());
        risk.setAvailability(dto.getAvailability());
        risk.setAssetRating(dto.getAssetRating());
        risk.setThreatIdentification(dto.getThreatIdentification());
        risk.setVulnerabilityIdentification(dto.getVulnerabilityIdentification());
        risk.setIncidentScenario(dto.getIncidentScenario());
        risk.setProbabilityOfOccurrence(dto.getProbabilityOfOccurrence());
        risk.setRiskLevel(dto.getRiskLevel());
        risk.setConsequences(dto.getConsequences());
        risk.setLevelOfConsequences(dto.getLevelOfConsequences());
        risk.setRiskOwner(userRepository.findById(dto.getRiskOwnerId()).orElse(null));
        risk.setRiskManagementDecision(dto.getRiskManagementDecision());
        risk.setSuggestedControls(dto.getSuggestedControls());
        risk.setCorrespondingIsoControls(dto.getCorrespondingIsoControls());
        risk.setRevisedProbabilityOfOccurrence(dto.getRevisedProbabilityOfOccurrence());
        risk.setRevisedLevelOfConsequences(dto.getRevisedLevelOfConsequences());
        risk.setResidualRisk(dto.getResidualRisk());
        risk.setFinalManagementDecision(dto.getFinalManagementDecision());

        risk = riskRepository.save(risk);
        return mapToDTO(risk);
    }

    @Override
    public void deleteRisk(Long id) {
        riskRepository.deleteById(id);
    }

    @Override
    public List<RiskDto> importRisks(List<RiskDto> risks) {
        List<Risk> entities = risks.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
        List<Risk> saved = riskRepository.saveAll(entities);
        return saved.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Mapping methods
    private RiskDto mapToDTO(Risk risk) {
        RiskDto dto = new RiskDto();
        dto.setId(risk.getId());
        dto.setRelatedAsset(risk.getRelatedAsset());
        dto.setSubAsset(risk.getSubAsset());
        dto.setAssetOwnerId(risk.getAssetOwner() != null ? risk.getAssetOwner().getId() : null);
        dto.setConfidentiality(risk.getConfidentiality());
        dto.setIntegrity(risk.getIntegrity());
        dto.setAvailability(risk.getAvailability());
        dto.setAssetRating(risk.getAssetRating());
        dto.setThreatIdentification(risk.getThreatIdentification());
        dto.setVulnerabilityIdentification(risk.getVulnerabilityIdentification());
        dto.setIncidentScenario(risk.getIncidentScenario());
        dto.setProbabilityOfOccurrence(risk.getProbabilityOfOccurrence());
        dto.setRiskLevel(risk.getRiskLevel());
        dto.setConsequences(risk.getConsequences());
        dto.setLevelOfConsequences(risk.getLevelOfConsequences());
        dto.setRiskOwnerId(risk.getRiskOwner() != null ? risk.getRiskOwner().getId() : null);
        dto.setRiskManagementDecision(risk.getRiskManagementDecision());
        dto.setSuggestedControls(risk.getSuggestedControls());
        dto.setCorrespondingIsoControls(risk.getCorrespondingIsoControls());
        dto.setRevisedProbabilityOfOccurrence(risk.getRevisedProbabilityOfOccurrence());
        dto.setRevisedLevelOfConsequences(risk.getRevisedLevelOfConsequences());
        dto.setResidualRisk(risk.getResidualRisk());
        dto.setFinalManagementDecision(risk.getFinalManagementDecision());
        return dto;
    }

    private Risk mapToEntity(RiskDto dto) {
        Risk risk = new Risk();
        risk.setRelatedAsset(dto.getRelatedAsset());
        risk.setSubAsset(dto.getSubAsset());
        risk.setAssetOwner(userRepository.findById(dto.getAssetOwnerId()).orElse(null));
        risk.setConfidentiality(dto.getConfidentiality());
        risk.setIntegrity(dto.getIntegrity());
        risk.setAvailability(dto.getAvailability());
        risk.setAssetRating(dto.getAssetRating());
        risk.setThreatIdentification(dto.getThreatIdentification());
        risk.setVulnerabilityIdentification(dto.getVulnerabilityIdentification());
        risk.setIncidentScenario(dto.getIncidentScenario());
        risk.setProbabilityOfOccurrence(dto.getProbabilityOfOccurrence());
        risk.setRiskLevel(dto.getRiskLevel());
        risk.setConsequences(dto.getConsequences());
        risk.setLevelOfConsequences(dto.getLevelOfConsequences());
        risk.setRiskOwner(userRepository.findById(dto.getRiskOwnerId()).orElse(null));
        risk.setRiskManagementDecision(dto.getRiskManagementDecision());
        risk.setSuggestedControls(dto.getSuggestedControls());
        risk.setCorrespondingIsoControls(dto.getCorrespondingIsoControls());
        risk.setRevisedProbabilityOfOccurrence(dto.getRevisedProbabilityOfOccurrence());
        risk.setRevisedLevelOfConsequences(dto.getRevisedLevelOfConsequences());
        risk.setResidualRisk(dto.getResidualRisk());
        risk.setFinalManagementDecision(dto.getFinalManagementDecision());
        return risk;
    }
}
