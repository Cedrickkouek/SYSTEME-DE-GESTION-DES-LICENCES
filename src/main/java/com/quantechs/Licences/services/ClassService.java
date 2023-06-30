package com.quantechs.Licences.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quantechs.Licences.entities.LeService;
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
        .cleService(creerServicePayload.getCleService())
        .IdProjet(creerServicePayload.getIdProjet()).build();

        serviceRepository.save(service);

        return service;
    }
    
    public List<LeService> listerService() //Permet de retouner une Liste de toutes les licences
    {                                    //qui sont dans le repository de licence
        return serviceRepository.findAll();
    }

    public LeService rechercheUnServiceParId(String idService) throws ServiceNonTrouverException
    {
        LeService licence = serviceRepository.findByidService(idService);

        if(licence!=null)
        {
            return licence;
        }
        else{
            throw new ServiceNonTrouverException("Le Service avec pour ID: "+idService+" n'a pas été trouvé!");
        }
    }

    public void supprimerServiceParId(String idService) {
        
         serviceRepository.deleteById(idService);
    
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
