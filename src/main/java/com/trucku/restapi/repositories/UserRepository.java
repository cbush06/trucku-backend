package com.trucku.restapi.repositories;

import java.util.Optional;

import com.trucku.restapi.models.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    
    Optional<User> findUserByEmail(String email);
}