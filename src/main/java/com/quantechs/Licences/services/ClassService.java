package com.quantechs.Licences.services;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.fasterxml.uuid.Generators;
import com.quantechs.Licences.entities.LeService;
//import com.quantechs.Licences.entities.Projet;
//import com.quantechs.Licences.enumeration.StatusProjet;
//import com.quantechs.Licences.entities.Licence;
import com.quantechs.Licences.enumeration.StatusService;
import com.quantechs.Licences.exceptions.ProjetNonTrouverException;
import com.quantechs.Licences.exceptions.ServiceNonTrouverException;
import com.quantechs.Licences.payloads.CreerServicePayload;
//import com.quantechs.Licences.repositories.LicenceRepository;
import com.quantechs.Licences.repositories.ProjetRepository;
import com.quantechs.Licences.repositories.ServiceRepository;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClassService {
    @Autowired

    private ProjetRepository projectRepository;
    private final ServiceRepository serviceRepository;
    //private LicenceRepository licenceRepository;

    public LeService creerService(@Valid CreerServicePayload creerServicePayload) throws ProjetNonTrouverException{
        LeService service = LeService.builder()
        .IdProjet(creerServicePayload.getIdProjet())
        .nomService(creerServicePayload.getNomService())
        .description(creerServicePayload.getDescription())
        .prix(creerServicePayload.getPrix())
        .URLLogo(creerServicePayload.getURLLogo())
        .responsable(creerServicePayload.getResponsable()).build();
        //.nombreLicence(creerServicePayload.getNombreLicence())
        //affecterIdProjet(idProjet, service);

        var pro = projectRepository.findByidProjet(creerServicePayload.getIdProjet());
        var numPro = pro.getNombreService();     //.getNombreLicence();
        pro.setNombreService(numPro+1);
        projectRepository.save(pro);

        boolean verification = projectRepository.existsById(creerServicePayload.getIdProjet());     

        var getidProjet = service.getIdProjet();
        //boolean verfier = getidService;()
        if(verification)
        {
            service.setIdProjet(getidProjet);
        }
        else{
            throw new ProjetNonTrouverException("L'ID du Projet: "+getidProjet+" n'a pas été trouvé \u274C!");
        }
        service.setStatusService(StatusService.DISPONIBLE);

         
        serviceRepository.save(service);

        var serviceActu = serviceRepository.findByidService(service.getIdService());
        var idServiceProjet = serviceActu.getIdProjet();
        var idService = serviceActu.getIdService();
        var hash = idService.hashCode();

        String etatS;
        if(serviceActu.getStatusService()==StatusService.DISPONIBLE)
        {
            etatS = "1";
        }
        else{
            etatS = "0";
        }

        String cle = idService+"-"+idServiceProjet+"-"+hash+"-"+etatS;
        service.setCleService(cle);
        serviceRepository.save(service);

        /*UUID uuid = Generators.timeBasedGenerator().generate();
        service.setCleService(uuid);*/

        
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
            //var numLicence = licenceRepository.findByidService(idService);
            //service.setNombreLicence(numLicence);

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
        //service.setNombreLicence(creerServicePayload.getNombreLicence());
        //service.setIdProjet(creerServicePayload.getIdProjet());
        
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

    /*public LeService changerEtatService(String idService, StatusService statusService) {
        
        LeService service = serviceRepository.findByidService(idService);

        service.setStatusService(statusService);

        serviceRepository.save(service);

       return service;
    }*/

        public LeService activerUnService(String idService)
    {
        LeService service = serviceRepository.findByidService(idService);

        var pro = projectRepository.findByidProjet(service.getIdProjet());
        var numPro = pro.getNombreService();     //.getNombreLicence();
        pro.setNombreService(numPro+1);
        projectRepository.save(pro);

        service.setStatusService(StatusService.DISPONIBLE);

        serviceRepository.save(service);

       return service;
    }

    public LeService desactiverUnService(String idService)
    {
        LeService service = serviceRepository.findByidService(idService);
        var pro = projectRepository.findByidProjet(service.getIdProjet());
        var numPro = pro.getNombreService();     //.getNombreLicence();
        pro.setNombreService(numPro-1);
        projectRepository.save(pro);

        service.setStatusService(StatusService.NONDISPONIBLE);
        serviceRepository.save(service);

       return service;
    }

    public void supprimerToutService() {
        serviceRepository.deleteAll();
    }

    public void affecterIdProjet(String idProjet, LeService service)
    {
        boolean verifierProjet = projectRepository.existsById(idProjet);

        if(verifierProjet)
        {
            service.setIdProjet(idProjet);
            serviceRepository.save(service);
        }

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
