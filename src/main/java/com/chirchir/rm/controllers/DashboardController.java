package com.chirchir.rm.controllers;

import com.chirchir.rm.dto.BusinessForm;
import com.chirchir.rm.dto.ListingForm;
import com.chirchir.rm.dto.PropertyForm;
import com.chirchir.rm.models.Business;
import com.chirchir.rm.models.Listing;
import com.chirchir.rm.models.Property;
import com.chirchir.rm.services.BusinessService;
import com.chirchir.rm.services.ListingService;
import com.chirchir.rm.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private BusinessService businessService;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private ListingService listingService;

    @GetMapping("/")
    public String home(){

        return "businesses";
    }

    @GetMapping("/businesses")
    public String dashboard(Model model){

        List<Business> businesses = businessService.findByOrderByIdAsc();

        model.addAttribute("businesses", businesses);

        return "businesses";
    }

    @GetMapping("/businesses/{id}")
    public String findById(@PathVariable("id") Long id, Model model){

        Business business = businessService.findById(id);

        if (business != null){

            model.addAttribute("business", business);
            model.addAttribute("propertyForm", new PropertyForm());
            model.addAttribute("properties", propertyService.findByBusinessId(id));

            return "business_details";
        }

        return "businesses";
    }

    @GetMapping("/businesses/new")
    public String addBusiness(BusinessForm businessForm){

        return "create_business";
    }

    @PostMapping("/businesses/new")
    public String createCustomer(@Valid BusinessForm businessForm, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return "create_business";
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

        businessService.save(business);

        return "redirect:/dashboard/businesses";
    }

    @GetMapping("/properties")
    public String loans(Model model){

        List<Property> properties = propertyService.findAllByOrderByIdAsc();

        model.addAttribute("properties", properties);

        return "properties";
    }

    @GetMapping("/properties/{id}")
    public String loanById(@PathVariable("id") Long id, Model model){

        Property property = propertyService.findById(id);

        Business business = businessService.findById(property.getBusinessId());

        model.addAttribute("property", property);
        model.addAttribute("business", business);
        model.addAttribute("listingForm", new ListingForm());
        model.addAttribute("listings", listingService.findByPropertyId(id));

        return "property_details";
    }

    @PostMapping("/properties/new/{id}")
    public String createLoan(@PathVariable("id") Long id, @Valid PropertyForm propertyForm, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){

            Business business = businessService.findById(id);

            model.addAttribute("business", business);
            model.addAttribute("property", propertyForm);
            model.addAttribute("properties", propertyService.findByBusinessId(id));

            return "business_details";
        }else{

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
            }

            return "redirect:/dashboard/businesses/" + id;
        }

    }

    @GetMapping("/listings")
    public String getAllListings(Model model){

        List<Listing> listings = listingService.findAllByOrderByIdAsc();

        model.addAttribute("listings", listings);

        return "listings";
    }

    @PostMapping("/listings/new/{id}")
    public String addListing(@PathVariable("id") Long id, @Valid ListingForm listingForm, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){

            Property property = propertyService.findById(id);
            Business business = businessService.findById(property.getBusinessId());

            model.addAttribute("property", property);
            model.addAttribute("business", business);
            model.addAttribute("listingForm", listingForm);
            model.addAttribute("listings", listingService.findByPropertyId(id));

            return "property_details";
        }else{

            Property property = propertyService.findById(id);

            if (property != null){

                Listing listing = new Listing();

                listing.setListingName(listingForm.getName());
                listing.setDescription(listingForm.getDescription());
                listing.setListingPrice(Integer.parseInt(listingForm.getPrice()));
                listing.setListingStatus("open");
                listing.setListingLocation(property.getPropertyLocation());
                listing.setListingDate( new Date().toString());
                listing.setPropertyId(property.getId());

                listingService.save(listing);
                property.setTotalListings( property.getTotalListings() + 1);
                propertyService.save(property);
            }

            return "redirect:/dashboard/properties/" + id;
        }

    }

}
