package br.com.fourcamp.smc.SMC.controller;

import br.com.fourcamp.smc.SMC.config.security.JwtResponse;
import br.com.fourcamp.smc.SMC.config.security.JwtTokenProvider;
import br.com.fourcamp.smc.SMC.dto.LoginRequest;
import br.com.fourcamp.smc.SMC.model.Patient;
import br.com.fourcamp.smc.SMC.usecase.PatientService;
import br.com.fourcamp.smc.SMC.exceptions.CustomException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller for managing patients.
 */
@RestController
@RequestMapping("/patients")
public class PatientController extends UserController<Patient> {
    private PatientService patientService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public PatientController(PatientService patientService) {
        super(patientService);
        this.patientService = patientService;
    }

    /**
     * Creates a new patient.
     *
     * @param patient the patient to create
     * @return the response entity with the created patient or an error message
     */
    @Operation(summary = "Create a new patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Patient created"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/register")
    public ResponseEntity<?> createPatient(@RequestBody Patient patient) {
        try {
            return createUser(patient);
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    /**
     * Updates an existing patient.
     *
     * @param patient the patient to update
     * @return the response entity with the updated patient or an error message
     */
    @Operation(summary = "Update an existing patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient updated successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found"),
            @ApiResponse(responseCode = "400", description = "CPF cannot be changed"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/update")
    public ResponseEntity<?> updatePatient(@RequestBody Patient patient) {
        try {
            return updateUser(patient);
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    /**
     * Gets a patient by their ID.
     *
     * @param request the request containing the patient ID
     * @return the response entity with the patient or an error message
     */
    @PostMapping("/find")
    @Operation(summary = "Get a patient by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient Found"),
            @ApiResponse(responseCode = "404", description = "Patient not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> getPatientById(@RequestBody Map<String, String> request) {
        try {
            String id = request.get("id");
            return getUserById(id, Patient.class);
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    /**
     * Gets all patients.
     *
     * @return the response entity with the list of patients or an error message
     */
    @Operation(summary = "Get all patients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Patients found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/all")
    public ResponseEntity<?> getAllPatients() {
        try {
            return getAllUsers(Patient.class);
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    /**
     * Logs in a patient.
     *
     * @param loginRequest the login request
     * @return the response entity with the JWT token or an error message
     */
    @PostMapping("/login")
    @Operation(summary = "Patient login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid credentials"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> loginPatient(@RequestBody LoginRequest loginRequest) {
        try {
            Patient patient = patientService.login(loginRequest.getEmail(), loginRequest.getPassword());
            UserDetails userDetails = User.withUsername(patient.getEmail())
                    .password(patient.getPassword())
                    .authorities("USER")
                    .build();
            final String token = jwtTokenProvider.generateToken(userDetails);
            return ResponseEntity.ok(new JwtResponse(token));
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }
}
