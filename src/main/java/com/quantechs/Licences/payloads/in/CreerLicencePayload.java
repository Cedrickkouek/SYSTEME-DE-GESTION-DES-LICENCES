package com.quantechs.Licences.payloads.in;

//import java.time.LocalDate;
//import java.util.UUID;

//import org.springframework.data.annotation.Id;
//import org.springframework.format.annotation.NumberFormat;
//import org.springframework.format.annotation.DateTimeFormat;

//import com.quantechs.Licences.enumeration.StatusLicence;

//import com.quantechs.Licences.entities.Licence;

//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;

//import com.quantechs.Licences.enumeration.StatusLicence;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreerLicencePayload {
    @NotBlank(message = "ID du Service requis")
    @NotNull
    //@Id
    private String idService;
    @NotBlank(message = "ID du Projet requis")
    @NotNull
    private String idProjet;
    //@NotBlank(message = "Le nom du service est requis")
    //@NotNull(message = "Le nom du servicede doit pas etre null")
    //private String nomService;
    //@NotBlank(message = "La date d'achat est requise")
    //@NotNull(message = "La date d'achat est requise")
    //@DateTimeFormat
    //private LocalDate dateAchat;
    @NotBlank(message = "ID utilisatuer requis (numero de telephone)")
    @NotNull(message = "L'ID ne doit pas etre null (numero de telephone)")
    private String idUtilisateur;
    @NotBlank(message = "Le nom de l'utilisateur est requis")
    @NotNull(message = "Le nom ne doit pas etre null")
    private String nomUtilisateur;
    //@NotBlank(message = "La description est requise")
    //@NotNull(message = "La description ne doit pas etre null")
    //private String description;
    //@NotBlank(message = "Le statut de la licence est requis")
    //@NotNull
    //private StatusLicence status;
    //@NotBlank(message = "Le nom de projet est requis")
    //@NotNull(message = "Le Prix est requis")
    //@NumberFormat
    //private double prix;
    ////private String idPaiement;
    //@NotBlank(message = "Date d'expiration requise")
    //@NotNull(message = "La Date d'expiration requise")
    //@DateTimeFormat
    //private LocalDate dateExpiration;
    //@NotBlank(message = "Validité requise")
    //@NotNull
    //private Boolean validite;
    //@NotBlank(message = "clé licence requise")
    //@NotNull(message = "La clé de la licence ne doit pas etre null")
    //@GeneratedValue(strategy = GenerationType.UUID)
}