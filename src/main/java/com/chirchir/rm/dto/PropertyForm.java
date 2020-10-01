package com.chirchir.rm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PropertyForm {

    @NotEmpty(message = "Property name field is required")
    @NotNull
    private String propertyName;

    @NotEmpty(message = "Property type field is required")
    @NotNull
    private String propertyType;

    @NotEmpty(message = "Property location field is required")
    @NotNull
    private String propertyLocation;

}
