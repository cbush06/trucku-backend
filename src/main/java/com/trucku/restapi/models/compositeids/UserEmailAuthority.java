package com.trucku.restapi.models.compositeids;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserEmailAuthority implements Serializable {

	private static final long serialVersionUID = 7157178941341596274L;

    private String email;
    private String authority;
    
}