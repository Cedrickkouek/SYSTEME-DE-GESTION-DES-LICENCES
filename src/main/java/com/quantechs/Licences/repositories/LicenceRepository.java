package com.quantechs.Licences.repositories;
import com.quantechs.Licences.entities.Licence;
//import com.quantechs.Licences.enumeration.StatusLicence;
import com.quantechs.Licences.entities.Projet;



//import java.util.UUID;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
//import java.util.List;
//import com.quantechs.Licences.enumeration.StatusLicence;

public interface LicenceRepository extends MongoRepository<Licence, String>{
    Licence findByidLicence(String idLicence);
    //Licence findByCleLicence(String cleLicence);

    Page<Licence> findAllByIdService(String IdService, Pageable pageable);

    Licence findBycleLicence(String cleLicence);
    boolean existsBycleLicence(String cleLicence);

    Licence findByPaiementKey(String paiementKey);

    Licence findByidService(String idService);

    Page<Licence> findBynomService(Pageable pageable);

    List<Projet> findByidProjet(String idProjet);

    /*List<Licence> findBynomService(String nomService);

    List<Licence> findByStatus(StatusLicence status);*/
}


