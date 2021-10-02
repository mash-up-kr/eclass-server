package com.mashup.eclassserver.constants

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

val DEFAULT_OBJECT_MAPPER = Jackson2ObjectMapperBuilder().build<ObjectMapper>()