package com.quantechs.Licences.repositories;
import com.quantechs.Licences.entities.Licence;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LicenceRepository extends MongoRepository<Licence, String>{
    
}
