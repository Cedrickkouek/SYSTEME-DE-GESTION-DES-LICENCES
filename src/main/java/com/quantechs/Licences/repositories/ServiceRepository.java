package com.quantechs.Licences.repositories;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.quantechs.Licences.entities.Service;

public interface ServiceRepository extends MongoRepository<Service , String>{
    
}