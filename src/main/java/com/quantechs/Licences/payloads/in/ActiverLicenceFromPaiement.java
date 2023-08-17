package com.quantechs.Licences.payloads.in;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActiverLicenceFromPaiement {
    @NotBlank(message = "Le paiementKey est requis")
    private String paiementKey;
    @NotBlank(message = "Le paiementSatus est requis")
    private String paiementStatus;
}
