package com.naduvani.realestate;

import com.naduvani.realestate.enities.*;

import java.util.List;

public class TestObjectsFactory {

    public static Property getOneProperty() {
        Property property = Property.builder()
                .description("A beautiful property")
                .address("123 Main St")
                .city("City")
                .state("State")
                .zipCode("12345")
                .price(100000.0)
                .bedrooms(3)
                .bathrooms(2)
                .size(1500.0)
                .type(PropertyType.APARTMENT)
                .status(PropertyStatus.FOR_SALE)
                .person(Person.builder().id(12345L).build())
//                .images(List.of(PropertyImage.builder().imageUrl("one.jpg").build(),
//                        PropertyImage.builder().imageUrl("two.jpg").build()))
                .build();
        return property;
    }
}
