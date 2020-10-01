package com.chirchir.rm.api;

import com.chirchir.rm.dto.PropertyForm;
import com.chirchir.rm.models.Business;
import com.chirchir.rm.models.Listing;
import com.chirchir.rm.models.Property;
import com.chirchir.rm.services.BusinessService;
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
@RequestMapping("/api/properties")
public class PropertyRestController {

    private BusinessService businessService;
    private PropertyService propertyService;

    @Autowired
    public PropertyRestController(PropertyService propertyService, BusinessService businessService){

        this.businessService = businessService;
        this.propertyService = propertyService;
    }

    @GetMapping("/")
    public ResponseEntity<Response<Property>> getAll(){

        Response<Property> response = new Response<>();
        List<Property> properties = propertyService.findAllByOrderByIdAsc();
        response.setResults(properties);
        response.setSuccess(true);
        response.setMessage("Success");
        response.setTotal(properties.size());
        return ResponseEntity.ok(response);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Property>> findById(@PathVariable("id") Long id){

        Property property = propertyService.findById(id);
        Response<Property> response = new Response<>();
        List<Property> properties = new ArrayList<>();

        if (property != null){
            properties.add(property);
            response.setTotal(1);
            response.setResults(properties);
            response.setMessage("Success");
            response.setSuccess(true);
            return ResponseEntity.ok(response);
        }

        response.setTotal(properties.size());
        response.setResults(properties);
        response.setSuccess(false);
        response.setMessage("Property Not Found!");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<Response<Property>> findByPropertyName(@RequestParam(name = "type") String type){

        List<Property> results = propertyService.findByPropertyType(type.toUpperCase());
        Response<Property> response = new Response<>();

        List<Property> properties = new ArrayList<>();

        if (results == null){

            response.setTotal(0);
            response.setMessage("No matching properties found");
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

    @PostMapping("/new/{id}")
    public ResponseEntity<Response<Property>> createProperty(@PathVariable("id") Long id, @RequestBody @Valid PropertyForm propertyForm, BindingResult bindingResult){

        List<Property> properties = new ArrayList<>();

        if (bindingResult.hasErrors()) {

            Response<Property> response = new Response<>();
            response.setMessage(bindingResult.getAllErrors().toString());
            response.setResults(properties);

            return ResponseEntity.ok(response);
        }

        Business business = businessService.findById(id);

        if (business != null){

            Property property = new Property();
            property.setBusinessId(id);
            property.setPropertyName(propertyForm.getPropertyName());
            property.setPropertyType(propertyForm.getPropertyType());
            property.setPropertyLocation(propertyForm.getPropertyLocation());
            property.setDateAdded(new Date().toString());

            propertyService.save(property);
            business.setTotalProperties( business.getTotalProperties() + 1);
            businessService.save(business);

            Response<Property> response = new Response<>();
            response.setSuccess(true);
            response.setTotal(1);
            response.setMessage("Property created successfully");

            return ResponseEntity.ok(response);

        }

        Response<Property> response = new Response<>();
        response.setSuccess(false);
        response.setTotal(0);
        response.setMessage("The business entity does not exist!");

        return ResponseEntity.ok( response );
    }
}
