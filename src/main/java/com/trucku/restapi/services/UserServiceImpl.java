package com.trucku.restapi.services;

import java.util.Optional;

import com.trucku.restapi.models.User;
import com.trucku.restapi.repositories.UserRepository;
import com.trucku.restapi.services.interfaces.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class UserServiceImpl implements UserService {

    private UserRepository userRepo;

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepo.findUserByEmail(email);
    }
    
}