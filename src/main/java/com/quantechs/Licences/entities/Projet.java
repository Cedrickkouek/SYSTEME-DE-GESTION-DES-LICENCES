package com.quantechs.Licences.entities;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//import enumeration.StatusProjet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document

public class Projet {
@Id
private String id;
private String nomProjet;
private String description;
private String statusProjet;
private int nombreService;
private String nomDirecteurProjet;
private LocalDate dateCreation;
private String URLLOgo;
private String cléProjet;


    
}
