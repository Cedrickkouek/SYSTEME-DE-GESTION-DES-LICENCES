package com.quantechs.Licences.repositories;

//import java.util.List;

//import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.quantechs.Licences.entities.Projet;
//import com.quantechs.Licences.enumeration.StatusProjet;

public interface ProjetRepository extends MongoRepository<Projet, String>{
    
    Projet findByidProjet(String idProjet);

    //List<Projet>findByNomProjet(String nomProjet);

    //List<Projet> findByStatutsProjet(StatusProjet statuts);
}
