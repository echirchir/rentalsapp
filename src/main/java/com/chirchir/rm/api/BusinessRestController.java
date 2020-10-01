package com.chirchir.rm.api;

import com.chirchir.rm.dto.BusinessForm;
import com.chirchir.rm.models.Business;
import com.chirchir.rm.services.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/businesses")
public class BusinessRestController {

    private BusinessService service;

    @Autowired
    public BusinessRestController(BusinessService service){

        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<Response<Business>> getAll(){

        Response<Business> response = new Response<>();
        List<Business> businesses = service.findByOrderByIdAsc();
        response.setResults(businesses);
        response.setMessage("Success");
        response.setSuccess(true);
        response.setTotal(businesses.size());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Business>> findById(@PathVariable("id") Long id){

        Business business = service.findById(id);
        Response<Business> response = new Response<>();
        List<Business> businesses = new ArrayList<>();

        if (business != null){
            businesses.add(business);
            response.setTotal(1);
            response.setResults(businesses);
            response.setSuccess(true);
            response.setMessage("Success");
            return ResponseEntity.ok(response);
        }

        response.setTotal(businesses.size());
        response.setMessage("Business Not Found");
        response.setResults(businesses);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<Response<Business>> findByLocation(@RequestParam(name = "location") String location){

        List<Business> results = service.findByLocation(location);
        Response<Business> response = new Response<>();

        response.setTotal(results.size());
        response.setResults(results);
        response.setSuccess(true);
        response.setMessage("Success");
        return ResponseEntity.ok(response);

    }

    @PostMapping("/new")
    public ResponseEntity<Response<Business>> createBusiness(@RequestBody @Valid BusinessForm businessForm, BindingResult bindingResult){

        List<Business> businesses = new ArrayList<>();

        if (bindingResult.hasErrors()) {

            Response<Business> response = new Response<>();
            response.setMessage(bindingResult.getAllErrors().toString());
            response.setResults(businesses);

            return ResponseEntity.ok(response);
        }

        Business business = new Business();
        business.setActive(true);
        business.setBusinessName(businessForm.getBusinessName());
        business.setBusinessManager(businessForm.getBusinessManager());
        business.setAddress(businessForm.getAddress());
        business.setPhoneNumber(businessForm.getPhoneNumber());
        business.setLocation(businessForm.getLocation());
        business.setEmailAddress(businessForm.getEmailAddress());
        business.setTotalProperties(0);
        business.setCreatedDate(new Date().toString());

        service.save(business);
        Response<Business> response = new Response<>();
        response.setSuccess(true);
        response.setTotal(businesses.size());
        response.setMessage("Business created successfully");

        return ResponseEntity.ok( response );
    }

}
