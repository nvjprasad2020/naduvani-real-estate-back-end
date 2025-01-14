package com.naduvani.realestate.model;

import com.naduvani.realestate.enities.PropertyStatus;
import com.naduvani.realestate.enities.PropertyType;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewPropertyRequest {
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
    private MultipartFile[] selectedFiles;
}
