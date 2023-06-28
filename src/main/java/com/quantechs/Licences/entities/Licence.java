package com.quantechs.Licences.entities;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;



//import enumeration.StatusLicence;
//import enumeration.validation;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Builder
@Document

public class Licence {
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
