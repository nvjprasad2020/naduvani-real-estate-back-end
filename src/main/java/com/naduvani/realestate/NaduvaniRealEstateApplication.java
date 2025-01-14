package com.naduvani.realestate;

import com.naduvani.realestate.enities.*;
import com.naduvani.realestate.person.PersonRepository;
import com.naduvani.realestate.propertty.PropertyEnquiryRepository;
import com.naduvani.realestate.propertty.PropertyImageRepository;
import com.naduvani.realestate.propertty.PropertyRepository;
import com.naduvani.realestate.security.entities.ERoleTypes;
import com.naduvani.realestate.security.entities.Role;
import com.naduvani.realestate.security.entities.User;
import com.naduvani.realestate.security.repository.RoleRepository;
import com.naduvani.realestate.security.repository.UserRepository;
import com.naduvani.realestate.security.services.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySources;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@SpringBootApplication
@RequiredArgsConstructor
public class NaduvaniRealEstateApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(NaduvaniRealEstateApplication.class, args);
    }

    private final PropertyRepository propertyRepository;
    private final PersonRepository personRepository;
    private final PropertyImageRepository imageRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final UserAuthService userAuthService;
    private final PropertyEnquiryRepository propertyEnquiryRepository;

    public void run(String... args) throws Exception {
        Role guest = Role.builder()
                .name(ERoleTypes.ROLE_GUEST)
                .build();
        roleRepository.save(guest);
        Role seller = Role.builder()
                .name(ERoleTypes.ROLE_SELLER)
                .build();
        roleRepository.save(seller);
        Role admin = Role.builder()
                .name(ERoleTypes.ROLE_ADMIN)
                .build();
        roleRepository.save(admin);
        Role agent = Role.builder()
                .name(ERoleTypes.ROLE_AGENT)
                .build();
        roleRepository.save(agent);

        User user = User.builder()
                .roles(Set.of(guest))
                .firstName("jp")
                .lastName("vis")
                .email("nvjprasad2020@gmail.com")
                .password("P123")
                .build();
        userRepository.save(user);

        Person person = Person.builder()
                .address(Address.builder()
                        .houseNumber("201")
                        .state("devon")
                        .city("Torquay")
                        .street("Hele Road")
                        .build())
                .emailId("nvjprasad2020@gmail.com")
                .mobileNumber1("1234555")
                .mobileNumber2("9999999")
                .firstName("jayaprasad")
                .lastName("viswanathan")
                .build();
        Person newPerson = personRepository.save(person);
        String[] propNames = "Lilly Home, Avenue Regent, Oxton, Calton, Midwinter, CrownHill, Maven Garden, Java Castle".split(",");
        List<String> imgs = new ArrayList<>();
        for (var i = 1; i <= 8; i++) {
            imgs.add("assets/images/img_" + i + ".jpg");
        }
        for (var i = 1; i <= 8; i++) {
            Property property = Property.builder()
                    .propertyName(propNames[i - 1])
                    .address("19,Raleigh Avenue")
                    .price(1233D)
                    .bathrooms(2)
                    .bedrooms(3)
                    .person(personRepository.findById(1l).orElse(null))
                    .description("Can make happy home")
                    .zipCode("TQ278s")
                    .status(PropertyStatus.FOR_SALE)
                    .titleImageUrl(imgs.get(i - 1))
                    .build();
            var savedProperty = propertyRepository.save(property);
            imgs.forEach(img -> {
                imageRepository.save(PropertyImage.builder()
                        .imageUrl(img)
                        .property(savedProperty)
                        .build());
            });

        }

        createEnquiries();
    }

    private void createEnquiries() {
        Enquiry enquiry = Enquiry.builder()
                .enquiryDate(new Date())
                .person(personRepository.findById(1L).get())
                .property(propertyRepository.findById(1L).get())
                .message("is the price negotiable")
                .build();
        propertyEnquiryRepository.save(enquiry);

        enquiry = Enquiry.builder()
                .enquiryDate(new Date())
                .person(personRepository.findById(1L).get())
                .property(propertyRepository.findById(1L).get())
                .message("Is pet allowed")
                .build();
        propertyEnquiryRepository.save(enquiry);

        enquiry = Enquiry.builder()
                .enquiryDate(new Date())
                .person(personRepository.findById(1L).get())
                .property(propertyRepository.findById(2L).get())
                .message("is smoking allwoed")
                .build();
        propertyEnquiryRepository.save(enquiry);
    }
}
