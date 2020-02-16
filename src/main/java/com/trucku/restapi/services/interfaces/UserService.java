package com.trucku.restapi.services.interfaces;

import java.util.Optional;

import com.trucku.restapi.models.User;

public interface UserService {

    Optional<User> findUserByEmail( String email );
    
}