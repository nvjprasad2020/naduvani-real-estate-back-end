package com.naduvani.realestate.security.entities;

import jakarta.persistence.*;
import lombok.*;
//import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;


//@Document(collection = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Data
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private ERoleTypes name;

    @Override
    public String getAuthority() {
        return name.toString();
    }
}
