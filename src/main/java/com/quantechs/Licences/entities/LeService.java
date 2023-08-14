package com.quantechs.Licences.entities;

import java.time.LocalDate;

//import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.quantechs.Licences.enumeration.StatusService;

import jakarta.persistence.Entity;

//import enumeration.StatusService;
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
public class LeService {
    @Id
    private String idService;
    private String idProjet;
    private String idPaiementProjet;
    private String statusPaiementProjet;
    private String nomService;
    private String description;
    //private String validation;
    private int montant;
    private StatusService statusService;
    private String URLLogo;
    private String responsable;
    private long nombreLicence;
    private String cleService;
    private String accronymeService;
    private LocalDate dateCreation;
}
