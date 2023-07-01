package com.quantechs.Licences.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quantechs.Licences.entities.Licence;
//import com.quantechs.Licences.enumeration.StatusLicence;
//import com.quantechs.Licences.enumeration.StatusLicence;
//import com.quantechs.Licences.exceptions.HttpMessageNotReadableExceptionn;
import com.quantechs.Licences.exceptions.LicenceNonTrouverException;
import com.quantechs.Licences.payloads.CreerLicencePayload;
import com.quantechs.Licences.services.LicenceService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
//import org.springframework.web.bind.annotation.PutMapping;


@RestController
@AllArgsConstructor
@RequestMapping(value = "/licence")
public class LicenceController {
    private final LicenceService licenceService;
    //private final StatusLicence status;

    @PostMapping(value = "/creerlicence")
    public ResponseEntity<Licence> creerLicence( @Valid @RequestBody CreerLicencePayload CreerLicencePayload){
        var res = licenceService.creerLicence(CreerLicencePayload);
        return new ResponseEntity<Licence>(res,HttpStatus.OK);
    }
    
    @GetMapping(value = "/listerlicences")
    public ResponseEntity<List<Licence>> listerToutesLicences()
    {
        return ResponseEntity.ok(licenceService.listerToutesLicences());
    }

    @GetMapping(value = "/{idLicence}")
    public ResponseEntity<Licence> rechercherUneLicenceParId(@PathVariable String idLicence) throws LicenceNonTrouverException
    {
        return ResponseEntity.ok(licenceService.rechercheUneLicenceParId(idLicence));    
    }

    @DeleteMapping(value = "/{idLicence}")
    public String supprimerUneLicenceParId(@PathVariable String idLicence)
    {
        licenceService.supprimerLicenceParId(idLicence);
        String msg = "La Licence avec pour ID: "+idLicence+" a été supprimée avec succès \u2705";
        return msg;
    }

    /*public ResponseEntity<List<Licence>> rechercherParNomLicence(@PathVariable String nomService)
    {
        return ResponseEntity.ok(licenceService.rechercheUneLicenceParNom(nomService));
    }

   @GetMapping(value = "/{status}")
    public ResponseEntity<List<Licence>> rechercherParStatus(@PathVariable StatusLicence status)
    {
        return ResponseEntity.ok(licenceService.rechercheParStatus(status));
    }*/

    //@PutMapping(value="/activerlicence")
    /*public ResponseEntity<Licence> activerLicence(String id, @RequestBody CreerLicencePayload CreerLicencePayload) {
        //
        
        return 
    }*/
}
