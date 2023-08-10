package com.quantechs.Licences.entities;

import java.time.LocalDate;
//import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.quantechs.Licences.enumeration.StatusProjet;

import jakarta.persistence.Entity;
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
public class Projet {
    @Id
    private String idProjet;
    private String idPaimentApi;    
    private String nomProjet;
    private String description;
    private StatusProjet statusProjet;
    private int nombreService;
    private String nomDirecteurProjet;
    private LocalDate dateCreation;
    private String urlLOgo;
    private String cleProjet;
}
