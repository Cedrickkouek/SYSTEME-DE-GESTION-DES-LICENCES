package com.quantechs.Licences.payloads.in;

import java.time.LocalDate;

import org.hibernate.validator.constraints.URL;
//import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.format.annotation.NumberFormat;

//import com.quantechs.Licences.enumeration.StatusProjet;

//import com.quantechs.Licences.enumeration.StatusProjet;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreerProjetPayload {
    //@NotBlank(message = "Le nom de projet est requis")
    @NotNull
    private String nomProjet;
    @NotBlank
    @NotNull(message = "La description du projet est requise")
    private String description;
    //@NotBlank(message = "Veuillez spécifier le satut du projet")
    //@NotNull(message = "Veuillez spécifier le satut du projet")
    //private StatusProjet statusProjet;
    //@NotBlank
    //@NotNull(message = "Veuillez spécifier le nombre de services du projet")
    //@NumberFormat
    //private int nombreService;
    @NotBlank
    @NotNull
    private String nomDirecteurProjet;
    //@NotBlank
    @NotNull(message = "La date de création du projet est requise")
    @DateTimeFormat
    private LocalDate dateCreation;
    @NotBlank
    @NotNull(message = "Le lien du logo est requis")
    @URL
    private String urlLOgo;
    //@NotBlank(message = "Clé de projet manquante")
    //@NotNull
    //private String cleProjet; 
}
