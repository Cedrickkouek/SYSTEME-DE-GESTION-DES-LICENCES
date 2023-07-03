package com.quantechs.Licences.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quantechs.Licences.entities.LeService;
//import com.quantechs.Licences.entities.Projet;
//import com.quantechs.Licences.enumeration.StatusProjet;
import com.quantechs.Licences.enumeration.StatusService;
//import com.quantechs.Licences.exceptions.ProjetNonTrouverException;
//import com.quantechs.Licences.enumeration.StatusLicence;
import com.quantechs.Licences.exceptions.ServiceNonTrouverException;
import com.quantechs.Licences.payloads.CreerServicePayload;
import com.quantechs.Licences.services.ClassService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/service")
public class ServiceController {
    private final ClassService classService;
    //private final StatusLicence status;

    @PostMapping(value = "/creerservice")
    public ResponseEntity<LeService> creerService( @Valid @RequestBody CreerServicePayload CreerServicePayload){
        var res = classService.creerService(CreerServicePayload);
        return new ResponseEntity<LeService>(res,HttpStatus.OK);
    }
    
    @GetMapping(value = "/listerservices")
    public ResponseEntity<List<LeService>> listerToutesLicences()
    {
        return ResponseEntity.ok(classService.listerService());
    }

    @GetMapping(value = "/rechercher/{idService}")
    public ResponseEntity<LeService> rechercherUnServiceParId(@PathVariable String idService) throws ServiceNonTrouverException
    {
        return ResponseEntity.ok(classService.rechercheUnServiceParId(idService));    
    }

    @DeleteMapping(value = "/{idService}")
    public String supprimerServiceParId(@PathVariable String idService)
    {
        classService.supprimerServiceParId(idService);
        String msg = "La Licence avec pour ID: "+idService+" a été supprimée avec succès \u2705";
        return msg;
    }

    @GetMapping(value = "/verification/{cleService}")
    public ResponseEntity<LeService> verificationProjetParCle(@PathVariable UUID cleService) throws ServiceNonTrouverException
    {
        return ResponseEntity.ok(classService.verifierServiceParCle(cleService));
    }

    @PutMapping(value = "changerStatutService/{idService}")
    public ResponseEntity<LeService> changerStatus(@PathVariable String idService, StatusService statusService) throws ServiceNonTrouverException
    {

        var res = classService.changerEtatService(idService, statusService);

        return new ResponseEntity<LeService>(res, HttpStatus.ACCEPTED);
    }

    /*@DeleteMapping(value = "/supprimerLesService")
    public void superToutProjet()
    {
        classService.supprimerToutProjet();
    }*/

    /*@GetMapping(value = "/{nomService}")
    public ResponseEntity<List<Licence>> rechercherParNomLicence(@PathVariable String nomService)
    {
        return ResponseEntity.ok(licenceService.rechercheUneLicenceParNom(nomService));
    }*/

   /*  @GetMapping(value = "/{status}")
    public ResponseEntity<List<Licence>> rechercherParStatus(@PathVariable StatusLicence status)
    {
        return ResponseEntity.ok(licenceService.rechercheParStatus(status));
    } */
}
