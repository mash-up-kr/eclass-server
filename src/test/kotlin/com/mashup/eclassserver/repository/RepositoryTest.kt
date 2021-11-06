package com.mashup.eclassserver.repository

import com.mashup.eclassserver.config.JpaConfig
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@DataJpaTest
@Import(JpaConfig::class)
annotation class RepositoryTest()
