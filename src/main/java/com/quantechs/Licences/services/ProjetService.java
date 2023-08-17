package com.quantechs.Licences.services;

import java.security.NoSuchAlgorithmException;
//import java.time.temporal.ChronoUnit;
import java.util.List;
//import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
//import org.springframework.data.mongodb.repository.MongoRepository;
import  org.springframework.stereotype.Service;

import com.quantechs.Licences.Utils.HashGenerator;
import com.quantechs.Licences.entities.LeService;
import com.quantechs.Licences.entities.Licence;
//import com.fasterxml.uuid.Generators;
//import com.quantechs.Licences.entities.Licence;
//import com.quantechs.Licences.entities.Licence;
import com.quantechs.Licences.entities.Projet;
import com.quantechs.Licences.enumeration.StatusLicence;
import com.quantechs.Licences.enumeration.StatusProjet;
import com.quantechs.Licences.enumeration.StatusService;
import com.quantechs.Licences.exceptions.PaiementNonValideException;
//import com.quantechs.Licences.exceptions.LicenceNonTrouverException;
import com.quantechs.Licences.exceptions.ProjetNonTrouverException;
import com.quantechs.Licences.payloads.in.CreerProjetPayload;
import com.quantechs.Licences.repositories.LicenceRepository;
import com.quantechs.Licences.repositories.ProjetRepository;
//import com.quantechs.Licences.repositories.ServiceRepository;
import com.quantechs.Licences.repositories.ServiceRepository;


import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProjetService {
    @Autowired 

    private ProjetRepository projetRepository;
    private final LicenceRepository licenceRepository;
    private final ServiceRepository serviceRepository;

        public Projet creerProjet(String idPaiementApi, CreerProjetPayload creerProjetPayload) throws PaiementNonValideException, NoSuchAlgorithmException {
        
        boolean verificationPaiement = verifierPaiementParId(idPaiementApi);

        if(verificationPaiement)
        {
            Projet projet = Projet.builder()
            .idPaimentApi(idPaiementApi)
            .nomProjet(creerProjetPayload.getNomProjet())
            .description(creerProjetPayload.getDescription())    
            .nomDirecteurProjet(creerProjetPayload.getNomDirecteurProjet())
            .dateCreation(creerProjetPayload.getDateCreation())
            .urlLOgo(creerProjetPayload.getUrlLOgo()).build();
            projet.setStatusProjet(StatusProjet.ENCOURS);
            projetRepository.save(projet);
            var projetActu = projetRepository.findBycleProjet(projet.getCleProjet());

            projet.setIdPaimentApi(idPaiementApi);

            projet.setNombreService(0);
        

            var idProjetActu = projetActu.getIdProjet();
            //var hash = idProjetActu.hashCode();
            var hash = HashGenerator.generateHash(idProjetActu);

            String etatP;
            if(projetActu.getStatusProjet()==StatusProjet.ENCOURS)
            {
                etatP = "1";
            }
            else
            {
                etatP = "0";
            }

            
            String cle = idProjetActu+"-"+hash+"-"+etatP;
            projet.setCleProjet(cle);
            projetRepository.save(projet);

        /*UUID uuid = Generators.timeBasedGenerator().generate();
        projet.setCleProjet(uuid);*/

            projetRepository.save(projet);
            return projet;
        }
        else
        {
            throw new PaiementNonValideException("Le paiement avec pour id "+idPaiementApi+" n'est pas valide, ce projet ne peut pas étre créer! \u274C");
        }

    }

    public Boolean verifierPaiementParId(String idPaiementApi)
    {
        /*final String URL =  "http://127.0.0.1:8100/paiements/";
        WebClient client = WebClient.create(URL);
        Boolean reponse =*/
        return true;
    }

    //public Projet modifierProjet(String idProjet, CreerProjetPayload creerProjetPayload) /*throws LicenceNonTrouverException*/
    /*{
        Projet projet = projetRepository.findByidProjet(idProjet);
        projet.setNomProjet(creerProjetPayload.getNomProjet());
        projet.setDescription(creerProjetPayload.getDescription());
        projet.setNombreService(creerProjetPayload.getNombreService());
        projet.setNomDirecteurProjet(creerProjetPayload.getNomDirecteurProjet());
        projet.setDateCreation(creerProjetPayload.getDateCreation());

        projetRepository.save(projet);

        return projet;
    }*/
    /*public Projet changerEtatProjet(String idProjet, StatusProjet statusProjet)
    {
        Projet projet = projetRepository.findByidProjet(idProjet);

        projet.setStatusProjet(statusProjet);

        projetRepository.save(projet);

       return projet;
    }*/

    public Projet activerUnProjet(String idProjet)
    {
        Projet projet = projetRepository.findByidProjet(idProjet);
        projet.setStatusProjet(StatusProjet.ENCOURS);

        var listLicenceProjet = licenceRepository.findAll(Sort.by(idProjet));

        for (Licence projet2 : listLicenceProjet) {
            projet2.setStatus(StatusLicence.ACTIF);
            licenceRepository.save(projet2);
        }


        var listServiceProjet = serviceRepository.findAll(Sort.by(idProjet));

        for (LeService service : listServiceProjet) {
            service.setStatusService(StatusService.DISPONIBLE);
            serviceRepository.save(service);
        }


        projetRepository.save(projet);

       return projet;
    }

    public Projet desactiverUnProjet(String idProjet)
    {
        Projet projet = projetRepository.findByidProjet(idProjet);
        projet.setStatusProjet(StatusProjet.TERMINER);

        var listLicenceProjet = licenceRepository.findAll(Sort.by(idProjet));

        for (Licence projet2 : listLicenceProjet) {
            projet2.setStatus(StatusLicence.NON_ACTIF);

            licenceRepository.save(projet2);
        }

        var listServiceProjet = serviceRepository.findAll(Sort.by(idProjet));

        for (LeService service : listServiceProjet) {
            service.setStatusService(StatusService.NONDISPONIBLE);
            serviceRepository.save(service);
        }

        projetRepository.save(projet);

       return projet;
    }

    public List<Projet> listerTousProjets(){
        return projetRepository.findAll();
    }

    public Projet rechercherUnProjetParId(String id) throws ProjetNonTrouverException
    {

        Projet projet = projetRepository.findByidProjet(id);

        if(projet!=null)
        {
            return projet;
        }
        else{
            throw new ProjetNonTrouverException("Le Projet avec pour ID: "+id+" n'a pas été trouvé!");
        }
    }
    /*public void supprimerProjetParId(String id)
    {
         projetRepository.deleteById(id);
    }*/

    public void supprimerToutProjet() {
        projetRepository.deleteAll();
    }

    public Projet verifierProjetParCle(String cleProjet) throws ProjetNonTrouverException
    {
        
        Projet projet = projetRepository.findBycleProjet(cleProjet);

        if(projet!=null)
        {
            return projet;
        }
        else{
            throw new ProjetNonTrouverException("Le Projet dont la clé est: "+cleProjet+" n'existe pas \u274C!");
        }
    }

    


    /*public List<Projet> rechercherProjetParNom(String nom){
        return projetRepository.findByNomProjet(nom);
    }*/

    /*public List<Projet> rechercherProjetParStatuts(StatusProjet statuts)
    {
        return projetRepository.findByStatutsProjet(statuts);
    }*/
}
