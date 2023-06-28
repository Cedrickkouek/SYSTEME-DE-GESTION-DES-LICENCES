package com.quantechs.Licences.dto;

import java.time.LocalDate;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LicenceRequest {
    private String idLicence;
    private String nomLicence;
    private LocalDate dateAchat;
    private String idUtilisateur;
    private String nomUtilisateur;
    private String status;
    private float prix;
    private String idPaiement;
    private LocalDate dateExpiration;
    private Boolean validite;
    private String cleLicence;
    
}