package com.quantechs.Licences.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quantechs.Licences.dto.LicenceRequest;
import com.quantechs.Licences.entities.Licence;
import com.quantechs.Licences.repositories.LicenceRepository;

@Service
public class LicenceService {
    @Autowired

    private LicenceRepository repository;


    //Permet d'enregistrer un objet "licence dans le repository License"
    public Licence enregistrerLicence(LicenceRequest licenceRequest){
        Licence licence = Licence.build(licenceRequest.getId(),licenceRequest.getIdLicence(), licenceRequest.getNomLicence(), licenceRequest.getDateAchat(), licenceRequest.getIdUtilisateur(), licenceRequest.getNomUtilisateur(), licenceRequest.getStatus(), licenceRequest.getPrix(), licenceRequest.getIdPaiement(), licenceRequest.getDateExpiration(), licenceRequest.getValidite(), licenceRequest.getCleLicence());
        return repository.save(licence);
    }

    public List<Licence> getAllLicence() //Permet de retouner une Liste de toutes les licences
    {                                    //qui sont dans le repository de licence
        return repository.findAll();
    }
}
