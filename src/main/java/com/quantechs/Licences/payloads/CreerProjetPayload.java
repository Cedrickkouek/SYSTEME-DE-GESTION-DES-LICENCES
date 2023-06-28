package com.quantechs.Licences.payloads;

import java.time.LocalDate;

import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreerProjetPayload {
    @NotBlank(message = "Le nom de projet est requis")
    @NotNull
    private String nomProjet;
    @NotBlank(message = "La description du projet est requise")
    @NotNull
    private String description;
    @NotBlank(message = "Veuillez spécifier le satut du projet")
    @NotNull
    private String statusProjet;
    @NotBlank(message = "Veuillez spécifier le nombre de services du projet")
    @NotNull
    @NumberFormat
    private int nombreService;
    @NotBlank(message = "Veuillez spécifier le nom du directeur du projet")
    @NotNull
    private String nomDirecteurProjet;
    @NotBlank(message = "la date de création du projet est requise")
    @NotNull
    @DateTimeFormat
    private LocalDate dateCreation;
    @NotBlank(message = "le lien du logo est requis")
    @NotNull
    @URL
    private String UrlLOgo;
    @NotBlank(message = "clé de projet manquante")
    @NotNull
    private String cléProjet; 
}
