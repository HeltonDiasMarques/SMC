package br.com.fourcamp.smc.SMC.controller;

import br.com.fourcamp.smc.SMC.dto.BookConsultationRequest;
import br.com.fourcamp.smc.SMC.dto.CancelConsultationRequest;
import br.com.fourcamp.smc.SMC.dto.ScheduleRequestDto;
import br.com.fourcamp.smc.SMC.model.Schedule;
import br.com.fourcamp.smc.SMC.usecase.ScheduleService;
import br.com.fourcamp.smc.SMC.exceptions.CustomException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleController.class);
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Operation(summary = "Create a new schedule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Schedule created successfully"),
            @ApiResponse(responseCode = "409", description = "Data integrity violation"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<String> createSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto) {
        try {
            scheduleService.saveSchedule(scheduleRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Schedule created successfully.");
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @Operation(summary = "Get schedule by doctor ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Schedule found"),
            @ApiResponse(responseCode = "404", description = "No schedules found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/doctor")
    public ResponseEntity<List<Schedule>> getDoctorSchedule(@RequestBody Map<String, String> request) {
        try {
            String doctorId = request.get("doctorId");
            List<Schedule> schedules = scheduleService.getScheduleByDoctorId(doctorId);
            return ResponseEntity.ok(schedules);
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        }
    }

    @Operation(summary = "Get schedule by patient ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Schedule found"),
            @ApiResponse(responseCode = "404", description = "No schedules found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/patient")
    public ResponseEntity<List<Schedule>> getPatientSchedule(@RequestBody Map<String, String> request) {
        try {
            String patientId = request.get("patientId");
            List<Schedule> schedules = scheduleService.getScheduleByPatientId(patientId);
            return ResponseEntity.ok(schedules);
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        }
    }

    @Operation(summary = "Book a consultation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consultation booked successfully"),
            @ApiResponse(responseCode = "404", description = "No available doctor found for the given specialty and time"),
            @ApiResponse(responseCode = "409", description = "Data integrity violation"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/book")
    public ResponseEntity<String> bookConsultation(@RequestBody BookConsultationRequest request) {
        try {
            scheduleService.bookConsultation(request);
            return ResponseEntity.ok("Consultation booked successfully.");
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @Operation(summary = "Cancel a consultation by patient ID and start time")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consultation cancelled successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/cancel")
    public ResponseEntity<String> cancelConsultation(@RequestBody CancelConsultationRequest request) {
        try {
            scheduleService.cancelConsultation(request.getPatientId(), request.getStartTime());
            return ResponseEntity.ok("Consultation cancelled successfully.");
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }
}