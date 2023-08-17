package com.quantechs.Licences.services;


import java.io.StringReader;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.quantechs.Licences.Utils.HashGenerator;
//import com.fasterxml.uuid.Generators;
import com.quantechs.Licences.entities.LeService;
import com.quantechs.Licences.entities.Licence;
import com.quantechs.Licences.enumeration.StatusLicence;
//import com.quantechs.Licences.entities.Projet;
//import com.quantechs.Licences.enumeration.StatusProjet;
//import com.quantechs.Licences.entities.Licence;
import com.quantechs.Licences.enumeration.StatusService;
import com.quantechs.Licences.exceptions.ActivationProjetPaiementException;
import com.quantechs.Licences.exceptions.CreerIdPaiementException;
import com.quantechs.Licences.exceptions.ProjetNonTrouverException;
import com.quantechs.Licences.exceptions.ServiceNonTrouverException;
import com.quantechs.Licences.payloads.in.ActivateDeactivatePayload;
import com.quantechs.Licences.payloads.in.CreerIdPaiementProjetPayload;
import com.quantechs.Licences.payloads.in.CreerServicePayload;
import com.quantechs.Licences.repositories.LicenceRepository;
//import com.quantechs.Licences.repositories.LicenceRepository;
import com.quantechs.Licences.repositories.ProjetRepository;
import com.quantechs.Licences.repositories.ServiceRepository;
import com.quantechs.Licences.payloads.out.projetInfos;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClassService {
    @Autowired
    private final CommunicationUtils communicationUtils;
    private final ProjetRepository projectRepository;
    private final ServiceRepository serviceRepository;
    private final LicenceRepository licenceRepository;

    public LeService creerService(@Valid CreerServicePayload creerServicePayload) throws ProjetNonTrouverException, CreerIdPaiementException, NoSuchAlgorithmException{
        LeService service = LeService.builder()
        .idProjet(creerServicePayload.getIdProjet())
        .nomService(creerServicePayload.getNomService())
        .description(creerServicePayload.getDescription())
        .montant(creerServicePayload.getMontant())
        .URLLogo(creerServicePayload.getURLLogo())
        .accronymeService(creerServicePayload.getAccronymeService())
        .responsable(creerServicePayload.getResponsable()).build();
        //.nombreLicence(creerServicePayload.getNombreLicence())
        //affecterIdProjet(idProjet, service);
        CreerIdPaiementProjetPayload cIpP = CreerIdPaiementProjetPayload.builder()
        .name(creerServicePayload.getNomService())
        .siteId(creerServicePayload.getSiteId())
        .apiKey(creerServicePayload.getApiKey())
        .notify_url(creerServicePayload.getNotify_url())
        .return_url(creerServicePayload.getReturn_url()).build();

        String responseCreerProjetPaiement = communicationUtils.creerIdPaiementProjet(cIpP);
        JsonObject jsonObject = Streams.parse(new JsonReader(new StringReader(responseCreerProjetPaiement))).getAsJsonObject();
        if(jsonObject.get("code").getAsString().equals("200"))
        {
            JsonObject data = jsonObject.get("data").getAsJsonObject();
            projetInfos projetInf = projetInfos.builder()
            .id(data.get("id").getAsString())
            .projectName(data.get("projectName").getAsString())
            .apiKey(data.get("apiKey").getAsString())
            .channelPayment(data.get("channelPayment").getAsString())
            //.notify_url(data.get("notify_url").getAsString())
            .stateProjet(data.get("stateProjet").getAsString())
            .siteId(data.get("siteId").getAsString())
            //.return_url(data.get("return_url").getAsString())
            .build();
            System.out.println(projetInf);

            if(projetInf.getStateProjet()!="ACTIF")
            {
                service.setStatusService(StatusService.NONDISPONIBLE);
            }

            service.setStatusPaiementProjet(projetInf.getStateProjet());
            service.setIdPaiementProjet(projetInf.getId());
            serviceRepository.save(service);
        }


        service.setNombreLicence(0);

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
        LocalDate now = LocalDate.now();
        service.setDateCreation(now);
         
        serviceRepository.save(service);

        var serviceActu = serviceRepository.findByidService(service.getIdService());
        var idServiceProjet = serviceActu.getIdProjet();
        var idService = serviceActu.getIdService();
        //var hash = idService.hashCode();
        var hash = HashGenerator.generateHash(idServiceProjet);

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
    /*public void supprimerToutService() {
            serviceRepository.deleteAll();
        }*/
    
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

    /*  
        public void supprimerServiceParId(String idService) {
        
        serviceRepository.deleteById(idService);
    
    }*/

    public LeService modifierService(String idService, CreerServicePayload creerServicePayload) /*throws LicenceNonTrouverException*/
    {
        LeService service = serviceRepository.findByidService(idService);
        service.setNomService(creerServicePayload.getNomService());
        service.setDescription(creerServicePayload.getDescription());
        service.setMontant(creerServicePayload.getMontant());
        service.setURLLogo(creerServicePayload.getURLLogo());
        service.setAccronymeService(creerServicePayload.getAccronymeService());
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

         var listLicenceService = licenceRepository.findAll(Sort.by(idService));

        for (Licence projet2 : listLicenceService) {
            projet2.setStatus(StatusLicence.ACTIF);
            licenceRepository.save(projet2);
        }

        var projet = projectRepository.findById(service.getIdProjet()).get();
        var numSerDansProjet = projet.getNombreService();
        projet.setNombreService(numSerDansProjet+1);
        projectRepository.save(projet);

        var cleService = service.getCleService();
        String[] partieCleService = cleService.split("-");
        partieCleService[3] = "1";
        String part1 = partieCleService[0];
        String part2 = partieCleService[1];
        String part3 = partieCleService[2];
        String part4 = partieCleService[3];
        String cle = part1+"-"+part2+"-"+part3+"-"+part4;

        service.setCleService(cle);

        service.setStatusService(StatusService.DISPONIBLE);

        LocalDate now = LocalDate.now();
        service.setDateCreation(now);


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

        var listLicenceService = licenceRepository.findAll(Sort.by(idService));

        for (Licence projet2 : listLicenceService) {
            projet2.setStatus(StatusLicence.NON_ACTIF);
            licenceRepository.save(projet2);
        }

        var cleService = service.getCleService();
        String[] partieCleService = cleService.split("-");
        partieCleService[3] = "0";
        String part1 = partieCleService[0];
        String part2 = partieCleService[1];
        String part3 = partieCleService[2];
        String part4 = partieCleService[3];
        String cle = part1+"-"+part2+"-"+part3+"-"+part4;

        service.setCleService(cle);

        String dateNull = "0000-00-00";
        LocalDate localDate = convertStringToLocalDate(dateNull);
        service.setDateCreation(localDate);

        service.setStatusService(StatusService.NONDISPONIBLE);
        serviceRepository.save(service);

       return service;
    }

    public void supprimerToutService() {
        serviceRepository.deleteAll();
    }

    public projetInfos activerProjetPaiement(ActivateDeactivatePayload activateDeactivatePayload) throws ActivationProjetPaiementException
    {
        LeService service = serviceRepository.findByIdPaiementProjet(activateDeactivatePayload.getId());
        String responseProjet = communicationUtils.activerPaimentProjet(activateDeactivatePayload);
        JsonObject jsonObject = Streams.parse(new JsonReader(new StringReader(responseProjet))).getAsJsonObject();
        if(jsonObject.get("code").getAsString().equals("200"))
        {
            JsonObject data = jsonObject.get("data").getAsJsonObject();
            projetInfos projetInf = projetInfos.builder()
            .id(data.get("id").getAsString())
            .projectName(data.get("projectName").getAsString())
            .apiKey(data.get("apiKey").getAsString())
            .channelPayment(data.get("channelPayment").getAsString())
            .stateProjet(data.get("stateProjet").getAsString())
            .siteId(data.get("siteId").getAsString()).build();
            //.return_url(data.get("return_url").getAsString())
            //.notify_url(data.get("notify_url").getAsString())
            
            service.setStatusPaiementProjet(projetInf.getStateProjet());
            serviceRepository.save(service);
            return projetInf;
        }
        else
        {
           throw new ActivationProjetPaiementException("Erreur lors de l'activation de project paiement");
        }
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

    /*public String genererIdPaiementProjet()
    {

    } */

     public static LocalDate convertStringToLocalDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }


}
