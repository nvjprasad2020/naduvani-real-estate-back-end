package com.naduvani.realestate.enities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//@Document(collection = "enquiries")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
public class Enquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne()
    @JoinColumn(name = "propertyId")
    private Property property;
    @ManyToOne()
    @JoinColumn(name = "personId")
    private Person person;
    private Date enquiryDate;
    private String message;
    private String[] replies;
}
