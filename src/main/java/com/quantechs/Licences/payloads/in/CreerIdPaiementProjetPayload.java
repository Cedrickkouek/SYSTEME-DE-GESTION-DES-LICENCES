package com.quantechs.Licences.payloads.in;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreerIdPaiementProjetPayload {
    @NotBlank(message = "le nom du projet est requis")
    private String name ;
    @NotBlank(message = "le site ID Du projet est Requis")
    private String siteId;
    @NotBlank(message = "le ApiKey du projet Est Requis")
    private String apiKey;
    @NotBlank(message = "le notify_url est requis")
    private String notify_url;
    @NotBlank(message = "le return url esr requis")
    private String return_url;
}
