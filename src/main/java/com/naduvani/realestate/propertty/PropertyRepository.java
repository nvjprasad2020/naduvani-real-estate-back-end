package com.naduvani.realestate.propertty;

import com.naduvani.realestate.enities.Person;
import com.naduvani.realestate.enities.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findAllByPerson(Person person);
}
