package com.chirchir.rm.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Business {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String businessName;

    private String businessManager;

    private String phoneNumber;

    private String emailAddress;

    private String location;

    private String address;

    private int totalProperties;

    private boolean active;

    private String createdDate;

    private String updatedDate;
}
