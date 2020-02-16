package com.trucku.restapi.security;

import java.util.Optional;

import com.trucku.restapi.models.User;
import com.trucku.restapi.services.interfaces.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserService userSvc;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userSvc.findUserByEmail(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Could not find user with email [%s]", username));
        }
        return user.get();
    }
    
}