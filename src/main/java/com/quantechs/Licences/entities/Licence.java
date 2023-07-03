package com.quantechs.Licences.entities;

import java.time.LocalDate;
//import java.util.UUID;
//import java.util.UUID;
import java.util.UUID;

//import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.quantechs.Licences.enumeration.StatusLicence;

import jakarta.persistence.Entity;
//import jakarta.annotation.Generated;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;


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
@Entity
public class Licence {
    @Id
    private String idLicence;
    private String idService;
    private String idProjet;
    private String nomService;
    private LocalDate dateAchat;
    private String idUtilisateur;
    private String nomUtilisateur;
    private StatusLicence status;
    private double prix;
    private String idPaiement;
    private LocalDate dateExpiration;
    private Boolean validite;
    //@GeneratedValue(strategy = GenerationType.UUID)

    /*@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "demo_sql")
    @GenericGenerator(name = "demo_sql", strategy = "com.quantechs.Licences.entities.PrefixDeGenerationDesCles", parameters = {

    })*/
    private UUID cleLicence;
    
}
