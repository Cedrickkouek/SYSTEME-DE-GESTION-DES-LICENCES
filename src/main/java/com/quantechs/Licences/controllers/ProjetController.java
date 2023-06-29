package com.quantechs.Licences.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.quantechs.Licences.entities.Projet;
//import com.quantechs.Licences.exceptions.LicenceNonTrouverException;
import com.quantechs.Licences.exceptions.ProjetNonTrouverException;
//import com.quantechs.Licences.enumeration.StatusProjet;
import com.quantechs.Licences.payloads.CreerProjetPayload;
import com.quantechs.Licences.services.ProjetService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

//import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@AllArgsConstructor
//@RequestMapping(value = "/projet")
public class ProjetController {
    private final ProjetService projetService;

    @PostMapping(value = "/projet/creerprojet")
    public ResponseEntity<Projet> creerProjet(@Valid @RequestBody CreerProjetPayload creerProjetPayload){
        var yes = projetService.creerProjet(creerProjetPayload);
        return new ResponseEntity<Projet>(yes,HttpStatus.OK);
    }

    @GetMapping(value = "/projet/listerprojets")
    public ResponseEntity<List<Projet>> listerProjets()
    {
        return ResponseEntity.ok(projetService.listerTousProjets());
    }

    @GetMapping(value = "/rechercher/{idProjet}")
    public ResponseEntity<Projet> rechercherUnProjetParId(@PathVariable String idProjet) throws ProjetNonTrouverException
    {
        return ResponseEntity.ok(projetService.rechercherUnProjetParId(idProjet));
    }

    @DeleteMapping(value = "/supprimer/{idProjet}")
    public String supprimerUneLicenceParId(@PathVariable String idProjet)
    {
        projetService.supprimerProjetParId(idProjet);
        String msg = "Le Projet avec pour ID: "+idProjet+" a été supprimée avec succès \u2705";
        return msg;
    }

    /*@GetMapping(value = "/{nomProjet}")
    public ResponseEntity<List<Projet>> rechercherParNomProjet(@RequestBody @PathVariable String nomProjet)
    {
        return ResponseEntity.ok(projetService.rechercherProjetParNom(nomProjet));
    }*/

    /*@GetMapping(value = "/{status}")
    public ResponseEntity<List<Projet>> rechercherProjetParStatus(@PathVariable StatusProjet Status)
    {
        return ResponseEntity.ok(projetService.rechercherProjetParStatus(Status));
    }*/

}
