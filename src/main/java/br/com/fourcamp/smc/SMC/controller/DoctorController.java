package br.com.fourcamp.smc.SMC.controller;

import br.com.fourcamp.smc.SMC.config.security.JwtResponse;
import br.com.fourcamp.smc.SMC.config.security.JwtTokenProvider;
import br.com.fourcamp.smc.SMC.dto.LoginRequest;
import br.com.fourcamp.smc.SMC.enums.Specialty;
import br.com.fourcamp.smc.SMC.model.Doctor;
import br.com.fourcamp.smc.SMC.usecase.DoctorService;
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

@RestController
@RequestMapping("/doctors")
public class DoctorController extends UserController<Doctor> {
    private DoctorService doctorService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        super(doctorService);
        this.doctorService = doctorService;
    }

    @Operation(summary = "Create a new doctor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Doctor created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/register")
    public ResponseEntity<?> createDoctor(@RequestBody Doctor doctor) {
        try {
            doctor.setSpecialty(Specialty.fromCode(doctor.getSpecialty().getCode()));
            return createUser(doctor);
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @Operation(summary = "Update an existing doctor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor updated successfully"),
            @ApiResponse(responseCode = "404", description = "Doctor not found"),
            @ApiResponse(responseCode = "400", description = "CPF cannot be changed"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/update")
    public ResponseEntity<?> updateDoctor(@RequestBody Doctor doctor) {
        try {
            doctorService.updateUser(doctor, Doctor.class);
            return ResponseEntity.ok(doctor);
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @Operation(summary = "Get a doctor by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor found"),
            @ApiResponse(responseCode = "404", description = "Doctor not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/find")
    public ResponseEntity<?> getDoctorById(@RequestBody Map<String, String> request) {
        try {
            String id = request.get("id");
            return getUserById(id, Doctor.class);
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @Operation(summary = "Get all doctors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctors found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/all")
    public ResponseEntity<?> getAllDoctors() {
        try {
            return getAllUsers(Doctor.class);
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @Operation(summary = "Doctor login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid credentials"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/login")
    public ResponseEntity<?> loginDoctor(@RequestBody LoginRequest loginRequest) {
        try {
            Doctor doctor = doctorService.login(loginRequest.getEmail(), loginRequest.getPassword());
            UserDetails userDetails = User.withUsername(doctor.getEmail())
                    .password(doctor.getPassword())
                    .authorities("USER")
                    .build();
            final String token = jwtTokenProvider.generateToken(userDetails);
            return ResponseEntity.ok(new JwtResponse(token));
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }
}