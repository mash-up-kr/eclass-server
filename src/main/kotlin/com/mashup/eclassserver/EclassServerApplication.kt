package com.mashup.eclassserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EclassServerApplication

fun main(args: Array<String>) {
    runApplication<EclassServerApplication>(*args)
}
