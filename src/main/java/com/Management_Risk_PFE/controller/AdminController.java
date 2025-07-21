package com.Management_Risk_PFE.controller;

import com.Management_Risk_PFE.dto.*;
import com.Management_Risk_PFE.entity.Meeting;
import com.Management_Risk_PFE.services.auth.MeetingService;
import com.Management_Risk_PFE.services.auth.Risk.RiskService;
import com.Management_Risk_PFE.services.auth.RiskTreatement.RiskTreatmentPlanService;
import com.Management_Risk_PFE.services.auth.admin.AdminService;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

    private final AdminService adminService;
    private final RiskService riskService; // ✅ Ajout du service Risk
    private final MeetingService meetingService; // ✅ Ajout du service Meeting
    private final RiskTreatmentPlanService riskTreatmentPlanService;  // <-- Injection service

    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        return adminService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        UserDto userDto = adminService.getUserById(id);
        if (userDto != null) {
            return ResponseEntity.ok(userDto); // Si l'utilisateur est trouvé
        } else {
            return ResponseEntity.notFound().build(); // Si l'utilisateur n'est pas trouvé
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id){
        adminService.deleteUser(id);
        return ResponseEntity.ok(null);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @ModelAttribute UserDto userDto) throws IOException {
        try {
            boolean success= adminService.updateUser(id,userDto);
            if (success) return ResponseEntity.status(HttpStatus.OK).build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // === RISKS ===

    @PostMapping("/risks")
    public ResponseEntity<RiskDto> createRisk(@RequestBody RiskDto riskDto) {
        RiskDto createdRisk = riskService.createRisk(riskDto);
        return new ResponseEntity<>(createdRisk, HttpStatus.CREATED);
    }

    @GetMapping("/risks/{id}")
    public ResponseEntity<RiskDto> getRiskById(@PathVariable Long id) {
        try {
            RiskDto riskDto = riskService.getRiskById(id);
            return ResponseEntity.ok(riskDto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/risks")
    public ResponseEntity<List<RiskDto>> getAllRisks() {
        return ResponseEntity.ok(riskService.getAllRisks());
    }

    @PutMapping("/risks/{id}")
    public ResponseEntity<RiskDto> updateRisk(@PathVariable Long id, @RequestBody RiskDto dto) {
        try {
            RiskDto updated = riskService.updateRisk(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/risks/{id}")
    public ResponseEntity<Void> deleteRisk(@PathVariable Long id) {
        riskService.deleteRisk(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/risks/import")
    public ResponseEntity<List<RiskDto>> importRisks(@RequestBody List<RiskDto> risks) {
        List<RiskDto> savedRisks = riskService.importRisks(risks);
        return new ResponseEntity<>(savedRisks, HttpStatus.CREATED);
    }

    // --- Risk Treatment Plans ---

    @PostMapping("/risk-treatment-plans")
    public ResponseEntity<RiskTreatmentPlanDTO> createRiskTreatmentPlan(@RequestBody RiskTreatmentPlanDTO dto) {
        RiskTreatmentPlanDTO created = riskTreatmentPlanService.createRiskTreatmentPlan(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }



    @PostMapping("/risk-treatment-plans/import")
    public ResponseEntity<List<RiskTreatmentPlanDTO>> importRiskTreatmentPlans(@RequestBody List<RiskTreatmentPlanDTO> rtps) {
        List<RiskTreatmentPlanDTO> savedRtp = riskTreatmentPlanService.importRiskTreatmentPlans(rtps);
        return new ResponseEntity<>(savedRtp, HttpStatus.CREATED);
    }

    @GetMapping("/risk-treatment-plans/{id}")
    public ResponseEntity<RiskTreatmentPlanDTO> getRiskTreatmentPlanById(@PathVariable Long id) {
        try {
            RiskTreatmentPlanDTO dto = riskTreatmentPlanService.getRiskTreatmentPlanById(id);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/risk-treatment-plans")
    public ResponseEntity<List<RiskTreatmentPlanDTO>> getAllRiskTreatmentPlans() {
        return ResponseEntity.ok(riskTreatmentPlanService.getAllRiskTreatmentPlans());
    }

    @PutMapping("/risk-treatment-plans/{id}")
    public ResponseEntity<RiskTreatmentPlanDTO> updateRiskTreatmentPlan(@PathVariable Long id, @RequestBody RiskTreatmentPlanDTO dto) {
        try {
            RiskTreatmentPlanDTO updated = riskTreatmentPlanService.updateRiskTreatmentPlan(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/risk-treatment-plans/{id}")
    public ResponseEntity<Void> deleteRiskTreatmentPlan(@PathVariable Long id) {
        riskTreatmentPlanService.deleteRiskTreatmentPlan(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/rtp-with-risk")
    public ResponseEntity<List<RiskTreatmentPlanWithRiskDTO>> getAllWithRiskDetails() {
        return ResponseEntity.ok(riskTreatmentPlanService.getAllWithRiskDetails());
    }


    // --- Meetings ---

    @PostMapping("/meetings")
    public ResponseEntity<Meeting> createMeeting(@RequestBody
                                                 MeetingDto meeting) {
        Meeting createdMeeting = meetingService.createMeeting(meeting);
        return new ResponseEntity<>(createdMeeting, HttpStatus.CREATED);
    }

    @GetMapping("/meetings")
    public ResponseEntity<List<Meeting>> getAllMeetings() {
        return ResponseEntity.ok(meetingService.getAllMeetings());
    }

    @DeleteMapping("/meetings/{id}")
    public ResponseEntity<Void> deleteMeeting(@PathVariable Long id) {
        meetingService.deleteMeeting(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/meetings/{id}/summary")
    public ResponseEntity<Meeting> updateMeetingSummary(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        String summary = payload.get("summary");
        Meeting updatedMeeting = meetingService.updateMeetingSummary(id, summary);
        if (updatedMeeting != null) {
            return ResponseEntity.ok(updatedMeeting);
        }
        return ResponseEntity.notFound().build();
    }
}