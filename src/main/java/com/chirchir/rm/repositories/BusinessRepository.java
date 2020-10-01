package com.chirchir.rm.repositories;

import com.chirchir.rm.models.Business;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusinessRepository extends JpaRepository<Business, Long> {

    List<Business> findAllByOrderByIdAsc();

    Business findByEmailAddress(String emailAddress);

    Business findByBusinessName(String businessName);

    Business findByPhoneNumber(String phoneNumber);

    List<Business> findByActive(boolean active);

    List<Business> findByLocation(String location);

    Business findByBusinessManager(String manager);
}
