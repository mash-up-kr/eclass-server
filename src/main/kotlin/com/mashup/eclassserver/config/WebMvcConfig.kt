package com.mashup.eclassserver.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig(private val loginInfoResolver: LoginInfoResolver) : WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(loginInfoResolver)
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**").also { corsRegistration ->
            corsRegistration.allowedHeaders("*")
            corsRegistration.allowedMethods("*")
        }
    }
}