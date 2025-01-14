package com.naduvani.realestate.propertty;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.naduvani.realestate.enities.Enquiry;
import com.naduvani.realestate.enities.Property;
import com.naduvani.realestate.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/properties")
public class PropertyController {

    private static final Logger LOG = LoggerFactory.getLogger(PropertyController.class);

    private final PropertyService propertyService;

    @Autowired
    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping
    public ResponseEntity<List<ListingProperty>> getAllProperties() {
        List<ListingProperty> properties = propertyService.getAllProperties();
        return new ResponseEntity<>(properties, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailedProperty> getPropertyById(@PathVariable Long id) {
        DetailedProperty property = propertyService.getPropertyById(id);
        return new ResponseEntity<>(property, HttpStatus.OK);
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<NewPropertyResponse> createProperty(
            @RequestPart("property") String jsonStringRequest,
            @RequestPart("selectedFiles") MultipartFile[] selectedFiles) {
        LOG.info("Saving new property");
        LOG.info("Property details: {}", jsonStringRequest);
        if (selectedFiles != null) {
            for (MultipartFile file : selectedFiles) {
                LOG.info("Received file: {} (Size: {})", file.getOriginalFilename(), file.getSize());
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            NewPropertyRequest request = mapper.readValue(jsonStringRequest, NewPropertyRequest.class);
            request.setSelectedFiles(selectedFiles);
            NewPropertyResponse response = propertyService.createProperty(request);
            return new ResponseEntity<>(
                    new NewPropertyResponse("Success", "Property saved successfully"), HttpStatus.CREATED);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Property> updateProperty(@PathVariable Long id, @RequestBody Property updatedProperty) {
        Property updated = propertyService.updateProperty(id, updatedProperty);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{propertyId}/comments/{personId}")
    public ResponseEntity<List<EnquiryResponse>> getPropertyComments(@PathVariable Long propertyId, @PathVariable Long personId) {
        LOG.info("Fetching all comments for property created by logged in user.{}-{}",
                propertyId, personId);
        var enquiries = propertyService.findAllEnquiries(propertyId, personId);
        return ResponseEntity.ok(enquiries);
    }

    @PostMapping("/{propertyId}/comments/{personId}")
    public ResponseEntity<EnquiryResponse> addPropertyComment(@RequestBody String message,
                                                     @PathVariable Long propertyId,
                                                     @PathVariable Long personId) {
        LOG.info("Adding an enquiry by {} for {}", personId, propertyId);
        return ResponseEntity.ok(propertyService.addComment(propertyId, personId, message));
    }
}
