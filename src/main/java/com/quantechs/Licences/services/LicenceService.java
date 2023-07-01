package com.quantechs.Licences.services;

import java.util.List;
//import java.util.UUID;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.uuid.Generators;
import com.quantechs.Licences.entities.Licence;
//import com.quantechs.Licences.exceptions.HttpMessageNotReadableExceptionn;
//import com.quantechs.Licences.exceptions.HttpMessageNotReadableExceptionn;
//import com.quantechs.Licences.enumeration.StatusLicence;
import com.quantechs.Licences.exceptions.LicenceNonTrouverException;
import com.quantechs.Licences.payloads.CreerLicencePayload;
import com.quantechs.Licences.repositories.LicenceRepository;
import com.quantechs.Licences.enumeration.StatusLicence;

//import jakarta.annotation.Generated;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;

@Service
public class LicenceService {
    @Autowired

    private LicenceRepository licenceRepository;


    //Permet d'enregistrer un objet "licence dans le repository License"
    public Licence creerLicence(CreerLicencePayload creerLicencePayload){
        Licence licence = Licence.builder()
        .nomService(creerLicencePayload.getNomService())
        .dateAchat(creerLicencePayload.getDateAchat())
        .idUtilisateur(creerLicencePayload.getIdUtilisateur())
        .nomUtilisateur(creerLicencePayload.getNomUtilisateur())
        .prix(creerLicencePayload.getPrix())
        .idPaiement(creerLicencePayload.getIdPaiement())
        .dateExpiration(creerLicencePayload.getDateExpiration()).build();
        //.cleLicence(creerLicencePayload.getCleLicence()).build();

        UUID uuid = Generators.timeBasedGenerator().generate();
        licence.setCleLicence(uuid);

        licenceRepository.save(licence);
        
            return licence;
    }

    public List<Licence> listerToutesLicences() //Permet de retouner une Liste de toutes les licences
    {                                    //qui sont dans le repository de licence
        return licenceRepository.findAll();
    }

    public void supprimerToutesLicences() //Permet de retouner une Liste de toutes les licences
    {                                    //qui sont dans le repository de licence
        licenceRepository.deleteAll();
    }

    public Licence rechercheUneLicenceParId(String id) throws LicenceNonTrouverException
    {
        Licence licence = licenceRepository.findByidLicence(id);

        if(licence!=null)
        {
            return licence;
        }
        else{
            throw new LicenceNonTrouverException("La licence avec pour ID: "+id+" n'a pas été trouvé \u274C!");
        }
    } 

    public Licence verifierLicenceParCle(UUID cleLicence) throws LicenceNonTrouverException
    {
        Licence licence = licenceRepository.findBycleLicence(cleLicence);
        if(licence!=null)
        {
            return licence;
        }
        else{
            throw new LicenceNonTrouverException("La Licence avec pour clé: "+cleLicence+" n'existe pas \u274C!");
        }
    }

    public void supprimerLicenceParId(String id)
    {
         licenceRepository.deleteById(id);
    }

    public Licence activerLicence(String idLicence) /*throws LicenceNonTrouverException*/
    {
        Licence licence = licenceRepository.findByidLicence(idLicence);
        licence.setStatus(StatusLicence.ACTIF);
        licenceRepository.save(licence);
        return licence;

        /*StatusLicence status = StatusLicence.ACTIF;
        if(rechercheUneLicenceParId(idLicence)!=null)
        {
            rechercheUneLicenceParId(idLicence).setStatus(status);
            
            return rechercheUneLicenceParId(idLicence); 
        }
        else 
        {
            throw new LicenceNonTrouverException("La licence avec pour ID: "+idLicence+" n'a pas été trouvé!");
        }*/  
    }

    public Licence desactiverLicence(String idLicence) /*throws LicenceNonTrouverException*/
    { 
        Licence licence = licenceRepository.findByidLicence(idLicence);

        licence.setStatus(StatusLicence.NONACTIF);
        licenceRepository.save(licence);
        return licence;

        //StatusLicence status = StatusLicence.NONACTIF;
        /*if(rechercheUneLicenceParId(idLicence)!=null)
        {
            rechercheUneLicenceParId(idLicence).setStatus(StatusLicence.ACTIF);
            
            return rechercheUneLicenceParId(idLicence); 
        }
        else 
        {
            throw new LicenceNonTrouverException("La licence avec pour ID: "+idLicence+" n'a pas été trouvé!");
        }*/  
    }

    /*public UUID genererLicence()
    {   
        @GeneratedValue(strategy = GenerationType.AUTO)
        private UUID key;
    }*/
    /*public List<Licence> rechercheUneLicenceParNom(String nom)
    {
        return licenceRepository.findBynomService(nom);
    }*/

    /*public List<Licence> rechercheParStatus(StatusLicence status)
    {
        return licenceRepository.findByStatus(status);
    }*/

    /*public Object activerLicence() {
        return null;
    }*/
}
