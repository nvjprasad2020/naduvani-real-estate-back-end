package com.naduvani.realestate.security.repository;

import com.naduvani.realestate.security.entities.ERoleTypes;
import com.naduvani.realestate.security.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERoleTypes name);
}
