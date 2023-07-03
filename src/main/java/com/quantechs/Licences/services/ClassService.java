package com.quantechs.Licences.services;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.uuid.Generators;
import com.quantechs.Licences.entities.LeService;
//import com.quantechs.Licences.entities.Licence;
import com.quantechs.Licences.enumeration.StatusService;
import com.quantechs.Licences.exceptions.ServiceNonTrouverException;
import com.quantechs.Licences.payloads.CreerServicePayload;
import com.quantechs.Licences.repositories.ServiceRepository;

import jakarta.validation.Valid;

@Service

public class ClassService {
    @Autowired
    private ServiceRepository serviceRepository;

    public LeService creerService(@Valid CreerServicePayload creerServicePayload) {
        LeService service = LeService.builder()
        .nomService(creerServicePayload.getNomService())
        .description(creerServicePayload.getDescription())
        .prix(creerServicePayload.getPrix())
        .URLLogo(creerServicePayload.getURLLogo())
        .responsable(creerServicePayload.getResponsable())
        .nombreLicence(creerServicePayload.getNombreLicence())
        .IdProjet(creerServicePayload.getIdProjet()).build();

        UUID uuid = Generators.timeBasedGenerator().generate();
        service.setCleService(uuid);

        service.setStatusService(StatusService.DISPONIBLE);
        serviceRepository.save(service);

        return service;
    }
    
    public List<LeService> listerService() //Permet de retouner une Liste de toutes les licences
    {                                    //qui sont dans le repository de licence
        return serviceRepository.findAll();
    }

    public LeService rechercheUnServiceParId(String idService) throws ServiceNonTrouverException
    {
        LeService service = serviceRepository.findByidService(idService);

        if(service!=null)
        {
            return service;
        }
        else{
            throw new ServiceNonTrouverException("Le Service avec pour ID: "+idService+" n'a pas été trouvé!");
        }
    }

    public void supprimerServiceParId(String idService) {
        
         serviceRepository.deleteById(idService);
    
    }

    public LeService modifierService(String idService, CreerServicePayload creerServicePayload) /*throws LicenceNonTrouverException*/
    {
        LeService service = serviceRepository.findByidService(idService);
        service.setNomService(creerServicePayload.getNomService());
        service.setDescription(creerServicePayload.getDescription());
        service.setPrix(creerServicePayload.getPrix());
        service.setURLLogo(creerServicePayload.getURLLogo());
        service.setResponsable(creerServicePayload.getResponsable());
        service.setNombreLicence(creerServicePayload.getNombreLicence());
        service.setIdProjet(creerServicePayload.getIdProjet());

        serviceRepository.save(service);

        return service;
    }

    public LeService verifierServiceParCle(UUID cleService) throws ServiceNonTrouverException
    {
        LeService service = serviceRepository.findBycleService(cleService);
        if(service!=null)
        {
            return service;
        }
        else{
            throw new ServiceNonTrouverException("Le Service avec pour clé: "+cleService+" n'existe pas \u274C!");
        }
    }

    public LeService changerEtatService(String idService, StatusService statusService) {
        
        LeService service = serviceRepository.findByidService(idService);

        service.setStatusService(statusService);

        serviceRepository.save(service);

       return service;
    }

    public void supprimerToutProjet() {
        serviceRepository.deleteAll();
    }

    /*public List<LeService> rechercheUnServiceParNom(String nom)
    {
        return serviceRepository.findBynomService(nom);
    }*/

    /*public List<LeService> rechercheParStatus(StatusService status)
    {
        return erviceRepository.findByStatus(status);
    }*/
}
