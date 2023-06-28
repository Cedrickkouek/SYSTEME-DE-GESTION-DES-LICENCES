package com.quantechs.Licences.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quantechs.Licences.entities.Licence;
import com.quantechs.Licences.enumeration.StatusLicence;
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

    public Licence rechercheUneLicenceParId(String id)
    {
        return licenceRepository.findByidLicence(id);
    }

    public List<Licence> rechercheUneLicenceParNom(String nom)
    {
        return licenceRepository.find(nom);
    }

    public List<Licence> rechercheParStatus(String status)
    {
        return licenceRepository.fin
    }
}
