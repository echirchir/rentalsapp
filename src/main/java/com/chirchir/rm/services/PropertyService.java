package com.chirchir.rm.services;

import com.chirchir.rm.models.Property;
import com.chirchir.rm.repositories.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {

    private PropertyRepository repository;

    @Autowired
    public PropertyService(PropertyRepository repository){

        this.repository = repository;
    }

    public List<Property> findAllByOrderByIdAsc(){

        return repository.findAllByOrderByIdAsc();
    }

    public List<Property> findByBusinessId(Long id){

        return repository.findByBusinessId(id);
    }

    public Property save(Property property){

        return repository.save(property);
    }

    public List<Property> findAll(){

        return repository.findAll();
    }

    public Property findById(Long id){

        return repository.findById(id).orElse(null);
    }

    public Property findByPropertyName(String name){

        return repository.findByPropertyName(name);
    }

    public List<Property> findByPropertyType(String type){

        return repository.findByPropertyType(type);
    }

    public List<Property> findByPropertyLocation(String location){

        return repository.findByPropertyLocation(location);
    }
}
