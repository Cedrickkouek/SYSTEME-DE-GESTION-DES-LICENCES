package com.quantechs.Licences.payloads;

import java.time.LocalDate;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreerLicencePayload {
    private String nomService;
    private LocalDate dateAchat;
    private String idUtilisateur;
    private String nomUtilisateur;
    private float prix;
    private String idPaiement;
    private LocalDate dateExpiration;
    private String cleLicence;
    
}