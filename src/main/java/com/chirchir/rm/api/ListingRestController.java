package com.chirchir.rm.api;

import com.chirchir.rm.dto.ListingForm;
import com.chirchir.rm.models.Listing;
import com.chirchir.rm.models.Property;
import com.chirchir.rm.services.ListingService;
import com.chirchir.rm.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/listings")
public class ListingRestController {

    private PropertyService propertyService;
    private ListingService listingService;

    @Autowired
    public ListingRestController(PropertyService propertyService, ListingService listingService){

        this.propertyService = propertyService;
        this.listingService = listingService;
    }

    @GetMapping("/")
    public ResponseEntity<Response<Listing>> getAll(){

        Response<Listing> response = new Response<>();
        List<Listing> listings = listingService.findAllByOrderByIdAsc();
        response.setResults(listings);
        response.setTotal(listings.size());
        response.setMessage("Success");
        response.setSuccess(true);
        return ResponseEntity.ok(response);

    }

    @GetMapping("/search")
    public ResponseEntity<Response<Listing>> findByStatus(@RequestParam(name = "status") String status){

        List<Listing> results = listingService.findByListingStatus(status.toLowerCase());
        Response<Listing> response = new Response<>();

        if (results == null){

            response.setTotal(0);
            response.setMessage("No matching listings found");
            response.setResults(null);
            response.setSuccess(false);

            return ResponseEntity.ok(response);
        }

        response.setResults(results);
        response.setTotal(results.size());
        response.setSuccess(true);
        response.setMessage("Success");

        return ResponseEntity.ok(response);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Listing>> findById(@PathVariable("id") Long id){

        Listing listing = listingService.findById(id);
        Response<Listing> response = new Response<>();
        List<Listing> listings = new ArrayList<>();

        if (listing != null){
            listings.add(listing);
            response.setTotal(1);
            response.setMessage("Success");
            response.setSuccess(true);
            response.setResults(listings);
            return ResponseEntity.ok(response);
        }

        response.setTotal(listings.size());
        response.setResults(listings);
        response.setMessage("Listing Not Found");
        response.setSuccess(false);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/new/{id}")
    public ResponseEntity<Response<Listing>> createListing(@PathVariable("id") Long id, @RequestBody @Valid ListingForm listingForm, BindingResult bindingResult){

        List<Listing> listings = new ArrayList<>();

        if (bindingResult.hasErrors()) {

            Response<Listing> response = new Response<>();
            response.setMessage(bindingResult.getAllErrors().toString());
            response.setSuccess(false);
            response.setResults(listings);

            return ResponseEntity.ok(response);
        }

        Property property = propertyService.findById(id);

        if (property != null){

            Listing listing = new Listing();

            listing.setListingName(listingForm.getName());
            listing.setDescription(listingForm.getDescription());
            listing.setListingPrice(Integer.parseInt(listingForm.getPrice()));
            listing.setListingStatus("OPEN");
            listing.setListingLocation(property.getPropertyLocation());
            listing.setListingDate( new Date().toString());
            listing.setPropertyId(property.getId());

            listingService.save(listing);
            property.setTotalListings( property.getTotalListings() + 1 );
            propertyService.save(property);

            Response<Listing> response = new Response<>();
            response.setSuccess(true);
            response.setTotal(1);
            response.setMessage("Listing created successfully");

            return ResponseEntity.ok(response);
        }

        Response<Listing> response = new Response<>();
        response.setSuccess(false);
        response.setTotal(0);
        response.setMessage("The property entity does not exist!");

        return ResponseEntity.ok( response );
    }
}
