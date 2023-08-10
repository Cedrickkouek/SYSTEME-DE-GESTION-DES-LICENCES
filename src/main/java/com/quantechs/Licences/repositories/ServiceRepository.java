package com.quantechs.Licences.repositories;
//import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.quantechs.Licences.entities.LeService;

public interface ServiceRepository extends MongoRepository<LeService , String>{
    LeService findByidService(String idService);
    Page<LeService> findAllByIdProjet(String IdProjet, Pageable pageable);

    LeService findBycleService(UUID cleService);

    //List<LeService>findByNomService(String nomService);

    //List<LeService> findByStatusService(StatusService status);
}