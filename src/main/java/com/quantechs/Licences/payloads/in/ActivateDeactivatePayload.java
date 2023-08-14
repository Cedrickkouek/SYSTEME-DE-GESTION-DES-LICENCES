package com.quantechs.Licences.payloads.in;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ActivateDeactivatePayload {
    @NotBlank(message = "Id projet requis ")
    private String id;
    private String stateProjet;
}
