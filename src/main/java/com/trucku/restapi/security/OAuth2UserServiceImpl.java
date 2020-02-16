package com.trucku.restapi.security;

import java.util.Optional;

import com.trucku.restapi.models.User;
import com.trucku.restapi.services.interfaces.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class OAuth2UserServiceImpl implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private UserService userSvc;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuthUser = delegate.loadUser(userRequest);
        Optional<User> optUser = userSvc.findUserByEmail((String) oAuthUser.getAttribute("email"));

        if(optUser.isEmpty()) {
            return oAuthUser;
        }

        User user = optUser.get();
        user.setAttributes(oAuthUser.getAttributes());
        return user;
    }

    
}