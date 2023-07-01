package com.quantechs.Licences.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quantechs.Licences.entities.Licence;
//import com.quantechs.Licences.exceptions.HttpMessageNotReadableExceptionn;
//import com.quantechs.Licences.exceptions.HttpMessageNotReadableExceptionn;
//import com.quantechs.Licences.enumeration.StatusLicence;
import com.quantechs.Licences.exceptions.LicenceNonTrouverException;
import com.quantechs.Licences.payloads.CreerLicencePayload;
import com.quantechs.Licences.repositories.LicenceRepository;

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
        .dateExpiration(creerLicencePayload.getDateExpiration())
        .cleLicence(creerLicencePayload.getCleLicence()).build();
        
        licenceRepository.save(licence);
        
            return licence;
    }

    public List<Licence> listerToutesLicences() //Permet de retouner une Liste de toutes les licences
    {                                    //qui sont dans le repository de licence
        return licenceRepository.findAll();
    }

    public Licence rechercheUneLicenceParId(String id) throws LicenceNonTrouverException
    {
        Licence licence = licenceRepository.findByidLicence(id);

        if(licence!=null)
        {
            return licence;
        }
        else{
            throw new LicenceNonTrouverException("La licence avec pour ID: "+id+" n'a pas été trouvé!");
        }
    } 

    public void supprimerLicenceParId(String id)
    {
         licenceRepository.deleteById(id);
    }
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
