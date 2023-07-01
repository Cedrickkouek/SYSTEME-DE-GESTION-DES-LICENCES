package com.quantechs.Licences.entities;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.quantechs.Licences.enumeration.StatusLicence;

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
    @Id
    private String idLicence;
    private String nomService;
    private LocalDate dateAchat;
    private String idUtilisateur;
    private String nomUtilisateur;
    private StatusLicence status;
    private double prix;
    private String idPaiement;
    private LocalDate dateExpiration;
    private Boolean validite;
    private String cleLicence;
    
    
}
