package com.quantechs.Licences.payloads.in;

import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.NumberFormat;

import com.quantechs.Licences.enumeration.StatusService;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreerServicePayload {
    //@NotBlank(message = "ID de projet requis")
    @NotNull
    private String IdProjet;
    @NotBlank(message = "Le nom du service est requis")
    @NotNull
    private String nomService;
    @NotBlank(message = "La description est requise")
    @NotNull
    private String description;
    //@NotBlank(message = "la validation du service est requise")
    //@NotNull
    //private String validation;
    //@NotBlank
    @NotNull(message = "Le prix du service est requis")
    @NumberFormat
    private int montant;
    //@NotBlank(message = "Veuillez spécifier le satut du service")
    //@NotNull
    private StatusService statusService;
    @NotBlank(message = "L'url du logo est requis est requis")
    @NotNull
    @URL
    private String URLLogo;
    @NotBlank(message = "responsable requis")
    @NotNull
    private String responsable;
    //@NotBlank(message = "le nom du projet est requis")
    //private String name ;
    @NotBlank(message = "le site ID Du projet est Requis")
    private String siteId;
    @NotBlank(message = "le ApiKey du projet Est Requis")
    private String apiKey;
    @NotBlank(message = "le notify_url est requis")
    private String notify_url;
    @NotBlank(message = "le return url esr requis")
    private String return_url;
    @NotBlank(message =  "laccronyme du Service est requis")
    private String accronymeService;
    //@NotBlank
    //@NotNull(message = "Le nombre de licence est requis")
    //@NumberFormat
    //private int nombreLicence;
    //@NotBlank(message = "clé de service requise")
    //@NotNull
    //private String cleService;
    
}
