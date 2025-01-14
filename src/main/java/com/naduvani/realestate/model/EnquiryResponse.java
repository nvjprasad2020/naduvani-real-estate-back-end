package com.naduvani.realestate.model;

import com.naduvani.realestate.enities.Person;
import com.naduvani.realestate.enities.Property;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnquiryResponse {
    private Date date;
    private String message;
    private String[] replies;
}
