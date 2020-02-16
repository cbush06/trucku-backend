package com.trucku.restapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class ThymeleafConfiguration {

    private ApplicationContext context;

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(context);
        templateResolver.setPrefix("classpath:/templates/");
        templateResolver.setSuffix(".xhtml");
        return templateResolver;
    }
}