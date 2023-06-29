package com.quantechs.Licences.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


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



public class Service {
    @Id
    private String id;
    private String nomService;
    private String description;
    private String validation;
    private float prix;
    private String statusService;
    private String URLLogo;
    private String responsable;
    private int nombreLicence;
    private String cleService;
    private String IdProjet;


}