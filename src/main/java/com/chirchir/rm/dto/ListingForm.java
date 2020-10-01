package com.chirchir.rm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListingForm {

    @NotEmpty(message = "Listing name field is required")
    @NotNull
    private String name;

    @NotNull(message = "Listing price field is required")
    @Min(1)
    private String price;

    @NotEmpty(message = "Listing description field is required")
    @NotNull
    private String description;
}
