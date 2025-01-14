package com.naduvani.realestate.enities;

import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.Id;

import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
//@Document(collection = "people")

public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", nullable = true)
    private Address address;
    private String mobileNumber1;
    private String mobileNumber2;
    private String emailId;
    private PersonType personType;
}
