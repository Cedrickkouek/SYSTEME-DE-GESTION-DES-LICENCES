package com.quantechs.Licences.repositories;
import com.quantechs.Licences.entities.Licence;
//import com.quantechs.Licences.enumeration.StatusLicence;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
//import java.util.List;
//import java.util.List;
//import com.quantechs.Licences.enumeration.StatusLicence;

public interface LicenceRepository extends MongoRepository<Licence, String>{
    Licence findByidLicence(String idLicence);

    Licence findBycleLicence(UUID cleLicence);

    /*List<Licence> findBynomService(String nomService);

    List<Licence> findByStatus(StatusLicence status);*/
}


