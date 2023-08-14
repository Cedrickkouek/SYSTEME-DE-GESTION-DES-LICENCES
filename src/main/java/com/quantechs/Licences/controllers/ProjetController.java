package com.quantechs.Licences.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.List;
//import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.quantechs.Licences.entities.Licence;
import com.quantechs.Licences.entities.Projet;
import com.quantechs.Licences.enumeration.StatusProjet;
import com.quantechs.Licences.exceptions.EnumerationNotFoundException;
import com.quantechs.Licences.exceptions.PaiementNonValideException;
//import com.quantechs.Licences.exceptions.LicenceNonTrouverException;
//import com.quantechs.Licences.exceptions.LicenceNonTrouverException;
import com.quantechs.Licences.exceptions.ProjetNonTrouverException;
import com.quantechs.Licences.payloads.in.CreerProjetPayload;
import com.quantechs.Licences.repositories.ProjetRepository;
import com.quantechs.Licences.services.ProjetService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

//import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/projet")
public class ProjetController {
    private final ProjetService projetService;
    private ProjetRepository projetRepository;

    @PostMapping(value = "/creerprojet/{idPaimentApi}")
    public ResponseEntity<Projet> creerProjet(@Valid @PathVariable("idPaimentApi") String idPaiementApi, @RequestBody CreerProjetPayload creerProjetPayload) throws PaiementNonValideException, EnumerationNotFoundException, NoSuchAlgorithmException{
        var yes = projetService.creerProjet(idPaiementApi, creerProjetPayload);
        return new ResponseEntity<Projet>(yes,HttpStatus.OK);
    }

    @PutMapping(value = "/modifier/{idProjet}")
    public ResponseEntity<Projet> modifierProjet(@Valid @PathVariable("idProjet") String idProjet, @RequestBody CreerProjetPayload creerProjetPayload){
       
        Projet projetMisAjour = projetRepository.findByidProjet(idProjet);

        projetMisAjour.setNomProjet(creerProjetPayload.getNomProjet());
        projetMisAjour.setDescription(creerProjetPayload.getDescription());
        //projetMisAjour.setNombreService(creerProjetPayload.getNombreService());
        projetMisAjour.setNomDirecteurProjet(creerProjetPayload.getNomDirecteurProjet());
        projetMisAjour.setDateCreation(creerProjetPayload.getDateCreation());

        projetRepository.save(projetMisAjour);

        return ResponseEntity.ok(projetMisAjour);
        
    }

    @GetMapping(value = "/verification/{cleProjet}")
    public ResponseEntity<Projet> verificationProjetParCle(@PathVariable String cleProjet) throws ProjetNonTrouverException
    {
        return ResponseEntity.ok(projetService.verifierProjetParCle(cleProjet));
    }

    @GetMapping(value = "/listerprojets")
    public ResponseEntity<List<Projet>> listerProjets()
    {
        return ResponseEntity.ok(projetService.listerTousProjets());
    }

    @PutMapping(value = "/activerProjet/{idProjet}")
    public ResponseEntity<Projet> activerStatus(@PathVariable String idProjet, StatusProjet statusProjet) throws ProjetNonTrouverException
    {
        var res = projetService.activerUnProjet(idProjet);

        //var pro = projetService.rechercherUnProjetParId(idProjet);

         //pro.setStatusProjet(statusProjet);

         return new ResponseEntity<Projet>(res, HttpStatus.ACCEPTED);
    }

    @PutMapping(value = "/desactiverProjet/{idProjet}")
    public ResponseEntity<Projet> desactiverStatus(@PathVariable String idProjet, StatusProjet statusProjet) throws ProjetNonTrouverException
    {
        var res = projetService.desactiverUnProjet(idProjet);

        //var pro = projetService.rechercherUnProjetParId(idProjet);
         //pro.setStatusProjet(statusProjet);
         return new ResponseEntity<Projet>(res, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/rechercher/{idProjet}")
    public ResponseEntity<Projet> rechercherUnProjetParId(@PathVariable String idProjet) throws ProjetNonTrouverException
    {
        return ResponseEntity.ok(projetService.rechercherUnProjetParId(idProjet));
    }

    /*@DeleteMapping(value = "/supprimer/{idProjet}")
    public String supprimerUneLicenceParId(@PathVariable String idProjet)
    {
        projetService.supprimerProjetParId(idProjet);
        String msg = "Le Projet avec pour ID: "+idProjet+" a été supprimée avec succès \u2705";
        return msg;
    }*/

    
    /*@DeleteMapping(value = "/supprimerLesProjets")
    public void superToutProjet()
    {
        projetService.supprimerToutProjet();
    }*/


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
