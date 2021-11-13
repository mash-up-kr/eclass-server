package com.mashup.eclassserver.config

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Configuration
@EnableJpaAuditing
class JpaConfig(@PersistenceContext private val entityManager: EntityManager) {

    @Bean
    fun jpaQueryFactory() = JPAQueryFactory(entityManager)
}