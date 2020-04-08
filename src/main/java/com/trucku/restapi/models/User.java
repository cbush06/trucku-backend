package com.trucku.restapi.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import lombok.Data;

@Entity
@Table(name = "USERS")
@Data
public class User implements UserDetails, OidcUser, Serializable {

    private static final long serialVersionUID = 7228329796427986022L;

    @Id
    @Size(max = 100)
    @Column(name = "EMAIL")
    private String email;

    @Size(max = 100)
    @Column(name = "FNAME")
    private String firstName;

    @Size(max = 100)
    @Column(name = "LNAME")
    private String lastName;

    @Size(max = 100)
    @Column(name = "PASSWORD")
    private String password;

    @NotNull
    @Column(name = "ENABLED")
    private Boolean enabled;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "EMAIL", referencedColumnName = "EMAIL", insertable = false, updatable = false, nullable = false)
    private Set<UserAuthority> authorities;

    //////////////////////////////////////////////
    // Methods implementing UserDetails follow...
    //////////////////////////////////////////////

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled.booleanValue();
    }

    //////////////////////////////////////////////
    // Methods implementing OAuth2User follow...
    //////////////////////////////////////////////

    @Transient
    @JsonIgnore
    private Map<String, Object> attributes = new HashMap<>();

    @Transient
    @JsonIgnore
    private Map<String, Object> claims = new HashMap<>();

    @Transient
    @JsonIgnore
    private OidcUserInfo userInfo;

    @Transient
    @JsonIgnore
    private OidcIdToken idToken;

    @Override
    public String getName() {
        return email;
    }
    
}