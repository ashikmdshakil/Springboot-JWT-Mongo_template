package com.quintet.apigateway.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
@Document
@Scope(scopeName = "prototype")
public class User {
    //user basic information
    @Id
    private String id;
    private String name;
    private String email;
    private String mobileNumber;
    private String password;

    private String ownerName;
    private String ownerMobileNumber;
    private String ownerEmail;
    private String ownerNID;

    //vendor's rating by consumers
    private double rating;

    //User Profile Picture
    private String image;

    //user address
    private double[] geoLocation;
    private String address;

    //user ip and date info
    private String registrationIp;
    //@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    /*@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)*/
    private String registrationDate;
    private String loginIp;
    private String lastLoginIp;

    //By defaults users are inactive
    private boolean active = false;
    //for branch request
    private boolean requested = false;
    private boolean vendorRequest = false;

    //Reference vendor for branch
    private User referenceVendor;
    private String vendorRegistrationCertificate;
    private String vendorVatCertificate;
    private String tradeLicense;
    private String drugLicense;
    private String registrationNumber;

    //SecurityToken
    @JsonIgnore
    @Transient
    private String JWTToken;

    //User roles
    private List<Role> roles = new ArrayList<>();

    
}
