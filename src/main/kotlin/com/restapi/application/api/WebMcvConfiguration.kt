package com.restapi.application.api

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport


@EnableWebMvc
class WebMvcConfiguration : WebMvcConfigurationSupport() {

    @Override
    override fun addCorsMappings(registry: CorsRegistry){
        //NOTE: servlet context set in "application.properties" is "/api" and request like "/api/session/login" resolves here to "/session/login"!
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "PUT", "DELETE","HEAD","OPTIONS")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowCredentials(false)
    }
}
