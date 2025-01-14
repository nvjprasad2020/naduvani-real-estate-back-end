package com.naduvani.realestate.propertty;

import com.naduvani.realestate.enities.Enquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PropertyEnquiryRepository extends JpaRepository<Enquiry, Long> {

    @Query("select e from Enquiry e where e.property.id = ?1 and e.person.id = ?2")
    List<Enquiry> findAllByPropertyAndPerson(Long propertyId, Long personId);

}
