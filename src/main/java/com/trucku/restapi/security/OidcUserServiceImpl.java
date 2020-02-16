package com.trucku.restapi.security;

import java.util.Optional;

import com.trucku.restapi.models.User;
import com.trucku.restapi.services.interfaces.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class OidcUserServiceImpl implements OAuth2UserService<OidcUserRequest, OidcUser> {

    private UserService userSvc;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUserService delegate = new OidcUserService();
        OidcUser oidcUser = delegate.loadUser(userRequest);
        Optional<User> optUser = userSvc.findUserByEmail((String) oidcUser.getAttribute("email"));

        if(optUser.isEmpty()) {
            return oidcUser;
        }

        User user = optUser.get();
        user.setAttributes(oidcUser.getAttributes());
        user.setClaims(oidcUser.getClaims());
        user.setUserInfo(oidcUser.getUserInfo());
        user.setIdToken(oidcUser.getIdToken());
        return user;
    }

    
}