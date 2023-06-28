package com.quantechs.Licences.repositories;

//import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.quantechs.Licences.entities.Projet;

public interface ProjetRepository extends MongoRepository<Projet, String>{
    
}
