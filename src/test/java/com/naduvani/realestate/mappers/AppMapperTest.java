package com.naduvani.realestate.mappers;

import com.naduvani.realestate.TestObjectsFactory;
import com.naduvani.realestate.enities.*;
import com.naduvani.realestate.model.ListingProperty;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class AppMapperTest {
    private static final Logger LOG = LoggerFactory.getLogger(AppMapperTest.class);
    @Autowired
    private AppMapper mapper;

//    @Test
    void testPropertyMappingWhenSuccess() {
        LOG.info("Start testing mapper class PropertyMinForList");
//        Property property = TestObjectsFactory.getOneProperty();
//        ListingProperty propMin = mapper.toPropertyMinForList(property);
//        Assert.assertEquals(propMin.getPersonId(), property.getPerson().getId());
//        Assert.assertEquals(property.getImages().get(0).getImageUrl(), propMin.getImageUrl());
//        Assertions.assertThat(property).isNotNull().isEqualTo(propMin.getDescription()).extracting(Property::getPerson)
//                .isEqualTo(propMin.getPersonId());
    }

}