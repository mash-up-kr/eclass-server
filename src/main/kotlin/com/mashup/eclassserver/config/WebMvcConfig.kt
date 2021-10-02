package com.mashup.eclassserver.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**").also { corsRegistration ->
            corsRegistration.allowedHeaders("*")
            corsRegistration.allowedMethods("*")
        }
    }
}