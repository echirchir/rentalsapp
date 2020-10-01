package com.chirchir.rm.services;

import com.chirchir.rm.models.Business;
import com.chirchir.rm.repositories.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BusinessService {

    private BusinessRepository repository;

    @Autowired
    public BusinessService(BusinessRepository repository){

        this.repository = repository;
    }

    public List<Business> findByOrderByIdAsc(){

        return repository.findAllByOrderByIdAsc();
    }

    public Business save(Business business){

        return repository.save(business);
    }

    public List<Business> findAll(){

        return repository.findAll();
    }

    public Business findById(Long id){

        return repository.findById(id).orElse(null);
    }

    public Business findByEmailAddress(String emailAddress){

        return repository.findByEmailAddress(emailAddress);
    }

    public Business findByBusinessName(String businessName){

        return repository.findByBusinessName(businessName);
    }

    public Business findByPhoneNumber(String phoneNumber){

        return repository.findByPhoneNumber(phoneNumber);
    }

    public List<Business> findByActive(boolean active){

        return repository.findByActive(active);
    }

    public List<Business> findByLocation(String location){

        return repository.findByLocation(location);
    }

    public Business findByBusinessManager(String manager){

        return repository.findByBusinessManager(manager);
    }
}
