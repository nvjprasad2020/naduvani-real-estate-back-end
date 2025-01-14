package com.naduvani.realestate.security.repository;

import com.naduvani.realestate.security.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String userName);
    boolean existsByEmail(String email);
}
