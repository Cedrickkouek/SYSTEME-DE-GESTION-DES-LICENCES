package com.quantechs.Licences.payloads;

import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.NumberFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreerServicePayload {
    @NotBlank(message = "Le nom du service est requis")
    @NotNull
    private String nomService;
    @NotBlank(message = "La description est requise")
    @NotNull
    private String description;
    @NotBlank(message = "la validation du service est requise")
    @NotNull
    private String validation;
    @NotBlank(message = "Le prix du service est requis")
    @NotNull
    @NumberFormat
    private float prix;
    @NotBlank(message = "Veuillez spécifier le satut du service")
    @NotNull
    private String statusService;
    @NotBlank(message = "L'url du logo est requis est requis")
    @NotNull
    @URL
    private String URLLogo;
    @NotBlank(message = "responsable requis")
    @NotNull
    private String responsable;
    @NotBlank(message = "Le nombre de licence est requis")
    @NotNull
    @NumberFormat
    private int nombreLicence;
    @NotBlank(message = "clé de service requise")
    @NotNull
    private String cleService;
    @NotBlank(message = "ID de produit requis")
    @NotNull
    private String IdProjet;
}