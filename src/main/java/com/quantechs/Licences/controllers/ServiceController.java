package com.quantechs.Licences.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quantechs.Licences.entities.LeService;
import com.quantechs.Licences.exceptions.ActivationProjetPaiementException;
import com.quantechs.Licences.exceptions.CreerIdPaiementException;
//import com.quantechs.Licences.entities.Projet;
//import com.quantechs.Licences.entities.Projet;
//import com.quantechs.Licences.enumeration.StatusProjet;
//import com.quantechs.Licences.enumeration.StatusService;
import com.quantechs.Licences.exceptions.ProjetNonTrouverException;
//import com.quantechs.Licences.exceptions.ProjetNonTrouverException;
//import com.quantechs.Licences.enumeration.StatusLicence;
import com.quantechs.Licences.exceptions.ServiceNonTrouverException;
import com.quantechs.Licences.payloads.in.ActivateDeactivatePayload;
import com.quantechs.Licences.payloads.in.CreerServicePayload;
import com.quantechs.Licences.payloads.out.projetInfos;
import com.quantechs.Licences.services.ClassService;
import com.quantechs.Licences.repositories.ServiceRepository;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/service")
public class ServiceController {
    private final ClassService classService;
    private ServiceRepository serviceRepository;
    //private final StatusLicence status;

    @PostMapping(value = "/creerservice")
    public ResponseEntity<LeService> creerService( @Valid @RequestBody CreerServicePayload CreerServicePayload) throws ProjetNonTrouverException, CreerIdPaiementException, NoSuchAlgorithmException, ServiceNonTrouverException{
        var res = classService.creerService(CreerServicePayload);
        return new ResponseEntity<LeService>(res, HttpStatus.OK);
    }
    @PutMapping(value = "/activerProjetPaiment")
    public ResponseEntity<projetInfos> activerProjetPaiement(@Valid @RequestBody ActivateDeactivatePayload activateDeactivatePayload) throws ActivationProjetPaiementException
    {
        var res = classService.activerProjetPaiement(activateDeactivatePayload);
        LeService serv = serviceRepository.findByIdPaiementProjet(activateDeactivatePayload.getId());
        serv.setStatusPaiementProjet(res.getStateProjet());
        serviceRepository.save(serv);
        return new ResponseEntity<projetInfos>(res, HttpStatus.OK);
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

   /*  @DeleteMapping(value = "/{idService}")
    public String supprimerServiceParId(@PathVariable String idService)
   {
        classService.supprimerServiceParId(idService);
        String msg = "La Licence avec pour ID: "+idService+" a été supprimée avec succès \u2705";
        return msg;
    } */

    @GetMapping(value = "/verification/{cleService}")
    public ResponseEntity<LeService> verificationProjetParCle(@PathVariable UUID cleService) throws ServiceNonTrouverException
    {
        return ResponseEntity.ok(classService.verifierServiceParCle(cleService));
    }

    /*@PutMapping(value = "changerStatutService/{idService}")
    public ResponseEntity<LeService> changerStatus(@PathVariable String idService, StatusService statusService) throws ServiceNonTrouverException
    {

        var res = classService.changerEtatService(idService, statusService);

        return new ResponseEntity<LeService>(res, HttpStatus.ACCEPTED);
    }*/

    @PutMapping(value = "activerService/{idService}")
    public ResponseEntity<LeService> activerService(@PathVariable String idService) throws ServiceNonTrouverException
    {
        var res = classService.activerUnService(idService);
        return new ResponseEntity<LeService>(res, HttpStatus.ACCEPTED);
    }

    @PutMapping(value = "desactiverService/{idService}")
    public ResponseEntity<LeService> desactiverService(@PathVariable String idService) throws ServiceNonTrouverException
    {
        var res = classService.desactiverUnService(idService);
        return new ResponseEntity<LeService>(res, HttpStatus.ACCEPTED);
    }

    @PutMapping(value = "modifier/{idService}")
    public ResponseEntity<LeService> modifierService(@Valid @PathVariable("idService") String idService, @RequestBody CreerServicePayload creerServicePayload){
       
        LeService serviceMisAjour = serviceRepository.findByidService(idService);

        serviceMisAjour.setNomService(creerServicePayload.getNomService());
        serviceMisAjour.setDescription(creerServicePayload.getDescription());
        serviceMisAjour.setMontant(creerServicePayload.getMontant());
        serviceMisAjour.setURLLogo(creerServicePayload.getURLLogo());
        serviceMisAjour.setResponsable(creerServicePayload.getResponsable());
        //serviceMisAjour.setNombreLicence(creerServicePayload.getNombreLicence());
        //serviceMisAjour.setIdProjet(creerServicePayload.getIdProjet());

        serviceRepository.save(serviceMisAjour);

        return ResponseEntity.ok(serviceMisAjour);
        
    }

    /*@DeleteMapping(value = "/supprimerLesService")
    public void superToutProjet()
    {
        classService.supprimerToutService();
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
