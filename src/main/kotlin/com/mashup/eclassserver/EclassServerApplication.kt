package com.mashup.eclassserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class EclassServerApplication

fun main(args: Array<String>) {
    runApplication<EclassServerApplication>(*args)
}
