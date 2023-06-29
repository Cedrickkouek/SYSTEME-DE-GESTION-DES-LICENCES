package com.quantechs.Licences.payloads;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreerLicencePayload {
    //@NotBlank(message = "ID requis")
    //@NotNull
    //@Id
    //private String idService;
    @NotBlank(message = "Le nom du service est requis")
    @NotNull(message = "Le nom du servicede doit pas etre null")
    private String nomService;
    //@NotBlank(message = "La date d'achat est requise")
    @NotNull(message = "La date d'achat est requise")
    @DateTimeFormat
    private LocalDate dateAchat;
    @NotBlank(message = "ID utilisatuer requis")
    @NotNull(message = "L'ID ne doit pas etre null")
    @Id
    private String idUtilisateur;
    @NotBlank(message = "Le nom de l'utilisateur est requis")
    @NotNull(message = "Le nom ne doit pas etre null")
    private String nomUtilisateur;
    //@NotBlank(message = "Le statut de la licence est requis")
    //@NotNull
    //private String status;
    //@NotBlank(message = "Le nom de projet est requis")
    @NotNull(message = "Le Prix est requis")
    @NumberFormat
    private float prix;
    @NotBlank(message = "L'ID du paiement est requis")
    @NotNull(message = "L'ID du paiment ne doit pas etre null")
    @Id
    private String idPaiement;
    //@NotBlank(message = "Date d'expiration requise")
    @NotNull(message = "La Date d'expiration requise")
    @DateTimeFormat
    private LocalDate dateExpiration;
    //@NotBlank(message = "Validité requise")
    //@NotNull
    //private Boolean validite;
    @NotBlank(message = "clé licence requise")
    @NotNull(message = "La clé de la licence ne doit pas etre null")
    private String cleLicence;
}