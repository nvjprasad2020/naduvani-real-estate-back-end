package com.naduvani.realestate.model;


import com.naduvani.realestate.enities.Enquiry;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class EnquiryListResponse {
    private String status;
    private List<EnquiryResponse> enquiryList;
}
