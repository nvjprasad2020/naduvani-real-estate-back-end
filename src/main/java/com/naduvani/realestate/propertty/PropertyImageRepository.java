package com.naduvani.realestate.propertty;

import com.naduvani.realestate.enities.Property;
import com.naduvani.realestate.enities.PropertyImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyImageRepository extends JpaRepository<PropertyImage, Long> {
    List<PropertyImage> findAllByProperty(Property property);
}
