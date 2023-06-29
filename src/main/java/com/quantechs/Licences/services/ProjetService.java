package com.quantechs.Licences.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import  org.springframework.stereotype.Service;

//import com.quantechs.Licences.entities.Licence;
import com.quantechs.Licences.entities.Projet;
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
        .dateCreation(creerProjetPayload.getDateCreation())
        .cleProjet(creerProjetPayload.getCleProjet()).build();
        
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

    /*public List<Projet> rechercherProjetParNom(String nom){
        return projetRepository.findByNomProjet(nom);
    }*/

    /*public List<Projet> rechercherProjetParStatuts(StatusProjet statuts)
    {
        return projetRepository.findByStatutsProjet(statuts);
    }*/
}
