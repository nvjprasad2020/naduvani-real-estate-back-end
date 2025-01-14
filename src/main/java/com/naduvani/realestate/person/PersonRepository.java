package com.naduvani.realestate.person;

import com.naduvani.realestate.enities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByEmailId(String emailId);
    // You can add custom queries here if needed
}
