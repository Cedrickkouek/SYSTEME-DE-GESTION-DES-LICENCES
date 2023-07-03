package com.quantechs.Licences.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import  org.springframework.stereotype.Service;

import com.fasterxml.uuid.Generators;
//import com.quantechs.Licences.entities.Licence;
//import com.quantechs.Licences.entities.Licence;
import com.quantechs.Licences.entities.Projet;
import com.quantechs.Licences.enumeration.StatusProjet;
//import com.quantechs.Licences.exceptions.LicenceNonTrouverException;
import com.quantechs.Licences.exceptions.ProjetNonTrouverException;
//import com.quantechs.Licences.payloads.CreerLicencePayload;
import com.quantechs.Licences.payloads.CreerProjetPayload;
import com.quantechs.Licences.repositories.ProjetRepository;

@Service
public class ProjetService {
    @Autowired 

    private ProjetRepository projetRepository;

        public Projet creerProjet(CreerProjetPayload creerProjetPayload){
        Projet projet = Projet.builder()
        .nomProjet(creerProjetPayload.getNomProjet())
        .description(creerProjetPayload.getDescription())
        .nombreService(creerProjetPayload.getNombreService())
        .nomDirecteurProjet(creerProjetPayload.getNomDirecteurProjet())
        .dateCreation(creerProjetPayload.getDateCreation()).build();
        
        projet.setStatusProjet(StatusProjet.ENCOURS);

        UUID uuid = Generators.timeBasedGenerator().generate();
        projet.setCleProjet(uuid);

        projetRepository.save(projet);
        return projet;
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
    public Projet changerEtatProjet(String idProjet, StatusProjet statusProjet)
    {
        Projet projet = projetRepository.findByidProjet(idProjet);

        projet.setStatusProjet(statusProjet);

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
            throw new ProjetNonTrouverException("La licence avec pour ID: "+id+" n'a pas été trouvé!");
        }
    }
    public void supprimerProjetParId(String id)
    {
         projetRepository.deleteById(id);
    }

    public void supprimerToutProjet() {
        projetRepository.deleteAll();
    }

    public Projet verifierProjetParCle(UUID cleProjet) throws ProjetNonTrouverException
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
