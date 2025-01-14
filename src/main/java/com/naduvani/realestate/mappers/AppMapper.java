package com.naduvani.realestate.mappers;

import com.naduvani.realestate.enities.Property;
import com.naduvani.realestate.model.DetailedProperty;
import com.naduvani.realestate.model.ListingProperty;
import com.naduvani.realestate.model.NewPropertyRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppMapper {
    ListingProperty toListingProperty(Property property);

    DetailedProperty toDetailedProperty(Property property);

    Property toPropertyFromNewProperty(NewPropertyRequest request);
}

