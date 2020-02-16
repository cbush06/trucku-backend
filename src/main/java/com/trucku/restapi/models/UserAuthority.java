package com.trucku.restapi.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.trucku.restapi.models.compositeids.UserEmailAuthority;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Entity
@IdClass(UserEmailAuthority.class)
@Table(name = "USERS_AUTHORITIES")
@Data
public class UserAuthority implements GrantedAuthority, Serializable  {

    private static final long serialVersionUID = -3596535920083143726L;

    @Id
    @Size(max = 100)
    @Column(name = "EMAIL")
    private String email;

    @Id
    @Size(max = 100)
    @Column(name = "AUTHORITY")
    private String authority;

}