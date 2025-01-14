package com.naduvani.realestate.enities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
//@Document(collection = "properties")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @Enumerated(EnumType.ORDINAL)
    private PropertyType type;
    @Enumerated(EnumType.ORDINAL)
    private PropertyStatus status;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;
    private String titleImageUrl;
}
