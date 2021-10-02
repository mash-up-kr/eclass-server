package com.mashup.eclassserver.infra

import org.slf4j.LoggerFactory

interface ECLogger {
    val log get() = LoggerFactory.getLogger(this::class.java)
}