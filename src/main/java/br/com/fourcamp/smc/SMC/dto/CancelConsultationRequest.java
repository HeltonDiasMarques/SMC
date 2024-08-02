package br.com.fourcamp.smc.SMC.dto;

import lombok.Data;

@Data
public class CancelConsultationRequest {
    private String patientId;
    private String startTime;
}
