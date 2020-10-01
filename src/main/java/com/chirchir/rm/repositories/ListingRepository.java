package com.chirchir.rm.repositories;

import com.chirchir.rm.models.Listing;
import com.chirchir.rm.models.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Long> {

    List<Listing> findAllByOrderByIdAsc();

    List<Listing> findByPropertyId(Long id);

    List<Listing> findByListingStatus(String status);
}
