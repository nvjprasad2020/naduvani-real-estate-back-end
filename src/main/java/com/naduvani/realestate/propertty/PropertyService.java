package com.naduvani.realestate.propertty;

import com.naduvani.realestate.enities.Enquiry;
import com.naduvani.realestate.enities.Person;
import com.naduvani.realestate.enities.Property;
import com.naduvani.realestate.enities.PropertyImage;
import com.naduvani.realestate.mappers.AppMapper;
import com.naduvani.realestate.model.*;
import com.naduvani.realestate.person.PersonRepository;
import com.naduvani.realestate.person.PersonService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private static final Logger LOG = LoggerFactory.getLogger(PropertyService.class);

    private final PropertyRepository propertyRepository;
    private final PropertyImageRepository imageRepository;
    private final AppMapper mapper;

    private final PersonRepository personRepository;

    private final PropertyEnquiryRepository propertyEnquiryRepository;
    @Value("${upload.image.dir}")
    private String imageUploadDir;

    public List<ListingProperty> getAllProperties() {
        var props = propertyRepository.findAll();
        LOG.info(" Fetched all properties {}", props.size());
        return props.stream().map(mapper::toListingProperty).toList();
    }

    public DetailedProperty getPropertyById(Long id) {
        Property property = propertyRepository.findById(id).orElse(null);
        List<PropertyImage> images = imageRepository.findAllByProperty(property);
        DetailedProperty detailedProperty = mapper.toDetailedProperty(property);
        detailedProperty.setImageUrls(images.stream()
                .map(PropertyImage::getImageUrl).toList());
        return detailedProperty;
    }

    public NewPropertyResponse createProperty(NewPropertyRequest request) {
        String imageRelativePath = "assets/images/";
        AtomicInteger index = new AtomicInteger(1);
        try {
            Property property = mapper.toPropertyFromNewProperty(request);
            property.setTitleImageUrl(imageRelativePath + property.getPropertyName() + "_1.jpg");
            property.setPerson(getLoggedInPerson());
            Property savedProperty = propertyRepository.save(property);
            if (null != request.getSelectedFiles() && request.getSelectedFiles().length != 0) {
                LOG.info("Saving images for properties");
                Arrays.stream(request.getSelectedFiles())
                        .forEach(
                                imgFile -> {
                                    File createdFile = new File(imageUploadDir,
                                            property.getPropertyName()
                                                    + "_" + index.getAndIncrement()
                                                    + ".jpg");
                                    try {
                                        imgFile.transferTo(createdFile);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    imageRepository.save(PropertyImage.builder()
                                            .imageUrl(imageRelativePath + createdFile.getName())
                                            .property(property)
                                            .build());
                                }
                        );
            }
            return new NewPropertyResponse("Success", "Property saved successfully");
        } catch (Exception e) {
            LOG.error("Error occurred while creating property: {}", e.getMessage());
            throw new PropertyServiceException("Error creating property", e);
        }
    }

    public Property updateProperty(Long id, Property updatedProperty) {
        if (propertyRepository.existsById(id)) {
            updatedProperty.setId(id);
            try {
                return propertyRepository.save(updatedProperty);
            } catch (Exception e) {
                LOG.error("Error occurred while updating property: {}", e.getMessage());
                throw new PropertyServiceException("Error updating property", e);
            }
        } else {
            LOG.warn("Property with id {} not found.", id);
            throw new PropertyNotFoundException("Property not found with id: " + id);
        }
    }

    public void deleteProperty(Long id) {
        try {
            propertyRepository.deleteById(id);
        } catch (Exception e) {
            LOG.error("Error occurred while deleting property: {}", e.getMessage());
            throw new PropertyServiceException("Error deleting property", e);
        }
    }

    public Person getLoggedInPerson() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            }
            return personRepository.findByEmailId(username);
        }
        return null;
    }

    public List<EnquiryResponse> findAllEnquiries(Long propertyId, Long personId) {
        LOG.info(" in service : fetching all comments.");

        List<EnquiryResponse> response =
                propertyEnquiryRepository.findAllByPropertyAndPerson(propertyId, personId)
                        .stream()
                        .map(e -> EnquiryResponse.builder()
                                .date(e.getEnquiryDate())
                                .message(e.getMessage())
                                .build()).toList();
        return response;
    }

    public EnquiryResponse addComment(Long propertyId, Long personId, String message) {
        Enquiry enquiry =  Enquiry.builder()
                .person(Person.builder().id(personId).build())
                .property(Property.builder().id(propertyId).build())
                .message(message)
                .enquiryDate(new Date())
                .build();
        Enquiry newEnquiry =  propertyEnquiryRepository.save(enquiry);
        EnquiryResponse response =  EnquiryResponse.builder()
                .message(newEnquiry.getMessage())
                .date(newEnquiry.getEnquiryDate())
                .build();
        return response;
    }
}
