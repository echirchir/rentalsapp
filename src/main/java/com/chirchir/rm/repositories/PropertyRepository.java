package com.chirchir.rm.repositories;

import com.chirchir.rm.models.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    List<Property> findAllByOrderByIdAsc();

    List<Property> findByBusinessId(Long id);

    List<Property> findByPropertyType(String type);

    Property findByPropertyName(String name);

    List<Property> findByPropertyLocation(String location);
}
