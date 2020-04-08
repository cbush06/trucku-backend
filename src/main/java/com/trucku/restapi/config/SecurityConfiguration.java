package com.trucku.restapi.config;

import java.util.List;

import javax.sql.DataSource;

import com.trucku.restapi.security.OAuth2UserServiceImpl;
import com.trucku.restapi.security.OidcUserServiceImpl;
import com.trucku.restapi.security.UserDetailsServiceImpl;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private OidcUserServiceImpl oidcUserSvc;
    private OAuth2UserServiceImpl oAuth2UserSvc;

    @Value("${frontendUrl}")
    private String frontendUrl;

    @Value("${cors.allowedOrigins}")
    private String[] allowedOrigins;

    @Value("${cors.allowedMethods}")
    private String[] allowedMethods;

    @Value("${cors.allowedHeaders}")
    private String[] allowedHeaders;
    
    @Value("${cors.allowCredentials}")
    private boolean allowCredentials;

    @Autowired
    public SecurityConfiguration(OidcUserServiceImpl oidcUserSvc, OAuth2UserServiceImpl oAuth2UserSvc) {
        this.oidcUserSvc = oidcUserSvc;
        this.oAuth2UserSvc = oAuth2UserSvc;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf().csrfTokenRepository(new HttpSessionCsrfTokenRepository()).and()
            .cors().and()
            .authorizeRequests()
                .antMatchers("/", "/images/**", "/css/**", "/js/**")
                    .permitAll()
                .anyRequest()
                    .authenticated().and()
                    .formLogin()
                        .loginPage("/")
                        .loginProcessingUrl("/dologin")
                        .defaultSuccessUrl("/success", true)
                        .failureUrl("/?error=true").and()
                    .oauth2Login()
                        .defaultSuccessUrl("/success", true)
                        .userInfoEndpoint()
                        .oidcUserService(oidcUserSvc)
                        .userService(oAuth2UserSvc).and().and()
                    .exceptionHandling()
                        .defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED), new AntPathRequestMatcher("/api/**"));
        
        // httpSecurity.csrf(c -> c.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));

        // httpSecurity.headers().frameOptions().sameOrigin();
    }

    @Autowired
    public void initialize(AuthenticationManagerBuilder auth, DataSource dataSource, UserDetailsServiceImpl userDetailsSvc) throws Exception {
        auth.userDetailsService(userDetailsSvc);
    }

    @Bean
    public HttpSessionIdResolver httpSessionIdResolver() {
        return HeaderHttpSessionIdResolver.xAuthToken(); 
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(ArrayUtils.add(allowedOrigins, frontendUrl)));
        config.setAllowedMethods(List.of(allowedMethods));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(allowCredentials);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}