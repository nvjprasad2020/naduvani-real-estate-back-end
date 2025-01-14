package com.naduvani.realestate.model;

import com.naduvani.realestate.enities.Person;
import com.naduvani.realestate.enities.PropertyStatus;
import com.naduvani.realestate.enities.PropertyType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListingProperty {
    private Long id;
    private String propertyName;
    private String description;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private Double price;
    private Integer bedrooms;
    private Integer bathrooms;
    private Double size;
    private PropertyType type;
    private PropertyStatus status;
    private Person person;
    private String titleImageUrl;
}
