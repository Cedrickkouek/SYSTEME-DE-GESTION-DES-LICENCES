package com.quantechs.Licences.repositories;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.quantechs.Licences.entities.LeService;

public interface ServiceRepository extends MongoRepository<LeService , String>{
    LeService findByidService(String idService);

    LeService findBycleService(UUID cleService);

    //List<LeService>findByNomService(String nomService);

    //List<LeService> findByStatusService(StatusService status);
}