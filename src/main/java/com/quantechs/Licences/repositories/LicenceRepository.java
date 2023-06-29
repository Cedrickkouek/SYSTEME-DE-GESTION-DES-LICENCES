package com.quantechs.Licences.repositories;
import com.quantechs.Licences.entities.Licence;
import org.springframework.data.mongodb.repository.MongoRepository;
//import java.util.List;
//import java.util.List;
//import com.quantechs.Licences.enumeration.StatusLicence;

public interface LicenceRepository extends MongoRepository<Licence, String>{
    Licence findByidLicence(String idLicence);

    //List<Licence> findBynomService(String nomService);

    //List<Licence> findByStatus(StatusLicence status);
}


