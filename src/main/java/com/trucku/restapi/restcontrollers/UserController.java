package com.trucku.restapi.restcontrollers;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.trucku.restapi.models.User;
import com.trucku.restapi.services.interfaces.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor(onConstructor = { @Autowired })
public class UserController {

	private final UserService userService;

    @GetMapping("/api/user")
    public User user(Authentication auth) {

        return (User) auth.getPrincipal();
		}
		
		@RequestMapping("/me")
		public ResponseEntity updateMe(@RequestBody User user, HttpServletRequest req, Authentication auth) {

			Optional<User> meUserOpt = userService.findUserByEmail(user.getEmail());
			if(!meUserOpt.isEmpty()) {
				User meUser = meUserOpt.get();
				Optional<User> updatedUser = userService.saveUser(meUser);
				return ResponseEntity.ok( updatedUser.get());
			}
			else return ResponseEntity.badRequest().build();
		}
}