package com.naduvani.realestate.enities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

//@Document(collection = "property_images")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Entity
@Builder
public class PropertyImage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imageUrl;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "propertyId")
    Property property;
}
