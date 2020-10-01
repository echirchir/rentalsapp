package com.chirchir.rm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessForm {

    @NotEmpty(message = "Business name field is required")
    @NotNull
    private String businessName;

    @NotEmpty(message = "Business manager field is required")
    @NotNull
    private String businessManager;

    @NotEmpty(message = "Phone number field is required")
    @NotNull
    private String phoneNumber;

    @NotEmpty(message = "Email address field is required")
    @NotNull
    @Email
    private String emailAddress;

    @NotEmpty(message = "Location field is required")
    @NotNull
    private String location;

    @NotEmpty(message = "Address field is required")
    @NotNull
    private String address;

}
