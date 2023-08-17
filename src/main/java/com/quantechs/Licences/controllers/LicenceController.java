package com.quantechs.Licences.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.List;
//import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quantechs.Licences.entities.Licence;
//import com.quantechs.Licences.entities.Projet;
import com.quantechs.Licences.enumeration.StatusLicence;
import com.quantechs.Licences.exceptions.ActivationLicencePaiementException;
import com.quantechs.Licences.exceptions.EnumerationNotFoundException;
import com.quantechs.Licences.exceptions.LicenceNonCreerException;
//import com.quantechs.Licences.enumeration.StatusProjet;
//import com.quantechs.Licences.enumeration.StatusLicence;
//import com.quantechs.Licences.enumeration.StatusLicence;
//import com.quantechs.Licences.exceptions.HttpMessageNotReadableExceptionn;
import com.quantechs.Licences.exceptions.LicenceNonTrouverException;
import com.quantechs.Licences.exceptions.PaiementNonEffectueException;
import com.quantechs.Licences.exceptions.ProjetNonTrouverException;
import com.quantechs.Licences.exceptions.ServiceNonTrouverException;
import com.quantechs.Licences.exceptions.VerificationPaiementKeyException;
import com.quantechs.Licences.payloads.in.ActiverLicenceFromPaiement;
import com.quantechs.Licences.payloads.in.CreerLicencePayload;
import com.quantechs.Licences.payloads.out.ResponseLicence;
import com.quantechs.Licences.repositories.LicenceRepository;
//import com.quantechs.Licences.repositories.LicenceRepository;
import com.quantechs.Licences.services.LicenceService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
//import org.springframework.web.bind.annotation.PutMapping;


@RestController
@AllArgsConstructor
@RequestMapping(value = "/licence")
public class LicenceController {
    private final LicenceService licenceService;
    private final LicenceRepository licenceRepository;
    //private final StatusLicence status;

    @PostMapping(value = "/Acheterlicence")
    public ResponseEntity<Licence> AcheterLicence( @Valid @RequestBody CreerLicencePayload CreerLicencePayload) throws ServiceNonTrouverException, EnumerationNotFoundException, PaiementNonEffectueException, LicenceNonCreerException, ProjetNonTrouverException, NoSuchAlgorithmException{
        var res = licenceService.AcheterLicence(CreerLicencePayload);
        return new ResponseEntity<Licence>(res,HttpStatus.OK);
    }

    @PutMapping(value="/activerlicence/{idLicence}")
    public ResponseEntity<Licence> activerLicence(@PathVariable String idLicence) throws LicenceNonTrouverException{
        var res = licenceService.activerLicence(idLicence); 

        return new ResponseEntity<Licence>(res, HttpStatus.ACCEPTED);
    }

    @PutMapping(value="/activerlicenceParPaiementKey")
    public ResponseEntity<ResponseLicence> activerLicenceParPaiementKey(@Valid @RequestBody ActiverLicenceFromPaiement activerLicenceFromPaiement) throws LicenceNonTrouverException, ActivationLicencePaiementException{
        var res = licenceService.activerLicenceParPaiementKey(activerLicenceFromPaiement); 

        return new ResponseEntity<ResponseLicence>(res, HttpStatus.ACCEPTED);
    }

    @PutMapping(value="/desactiverlicence/{idLicence}")
    public ResponseEntity<Licence> deactiverLicence(@PathVariable String idLicence) throws LicenceNonTrouverException{
        var res = licenceService.desactiverLicence(idLicence); 

        return new ResponseEntity<Licence>(res, HttpStatus.ACCEPTED);
    }
    
    @GetMapping(value = "/listerlicences")
    public ResponseEntity<List<Licence>> listerToutesLicences()
    {
        return ResponseEntity.ok(licenceService.listerToutesLicences());
    }

    /*@DeleteMapping(value = "/supprimerLeslicences")
    public void superToutesLicences()
    {
        licenceService.supprimerToutesLicences();
    }*/

    @GetMapping(value = "/rechercherId/{idLicence}")
    public ResponseEntity<Licence> rechercherUneLicenceParId(@PathVariable String idLicence) throws LicenceNonTrouverException
    {
        return ResponseEntity.ok(licenceService.rechercheUneLicenceParId(idLicence));    
    }
    @GetMapping(value = "/rechercherNumTelephoneEtActive/{numeroTelephone}")
    public ResponseEntity<List<Licence>> rechercherUneLicenceNumero(@PathVariable String numeroTelephone) throws LicenceNonTrouverException
    {
        return ResponseEntity.ok(licenceService.rechercherParNumeroEtStatusActif(numeroTelephone));    
    }

    /* @GetMapping(value = "/rechercherPaiement/{paiementKey}")
    public ResponseEntity<Licence> rechercherUneLicenceParPaiementKey(@PathVariable String paiementKey) throws LicenceNonTrouverException
    {
        return ResponseEntity.ok(licenceRepository.findByPaiementKey(paiementKey));    
    }
    */

    @GetMapping(value = "/verificationParCle/{cleLicence}")
    public ResponseLicence verificationLicenceParCle(@PathVariable String cleLicence) throws LicenceNonTrouverException, VerificationPaiementKeyException, LicenceNonCreerException, NoSuchAlgorithmException 
    {
        var res = licenceService.verifierLicence(cleLicence);
        return res;

        /*String msg = "La Licene avec pour clé: "+cleLicence+" est Valid \u2705";
        return msg;*/
    }

    /*@DeleteMapping(value = "supprimer/{idLicence}")
    public String supprimerUneLicenceParId(@PathVariable String idLicence)
    {
        licenceService.supprimerLicenceParId(idLicence);
        String msg = "La Licence avec pour ID: "+idLicence+" a été supprimée avec succès \u2705";
        return msg;
    }*/


    @PutMapping(value = "changerStatusLicence/{idLicence}")
    public ResponseEntity<Licence> changerStatus(@PathVariable String idLicence, StatusLicence statusLicence) throws LicenceNonTrouverException
    {

        var res = licenceService.changerEtatLicence(idLicence, statusLicence); 
        //var pro = projetService.rechercherUnProjetParId(idProjet);

         //pro.setStatusProjet(statusProjet);

         return new ResponseEntity<Licence>(res, HttpStatus.ACCEPTED);
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

  @GetMapping(value = "/deleteAll")
   public void supprimerAll()
   {
        licenceRepository.deleteAll();
   }
}
