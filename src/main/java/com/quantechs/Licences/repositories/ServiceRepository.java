package com.quantechs.Licences.repositories;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.quantechs.Licences.entities.LeService;

public interface ServiceRepository extends MongoRepository<LeService , String>{
    LeService findByidService(String idService);

    //List<LeService>findByNomService(String nomService);

    //List<LeService> findByStatusService(StatusService status);
}