package com.idealista.acceptance.config

import com.idealista.acceptance.config.stubs.InMemoryPersistence
import com.idealista.infrastructure.di.BeansInitializer
import io.cucumber.spring.CucumberContextConfiguration
import org.springframework.boot.test.context.SpringBootContextLoader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Profile
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(loader = SpringBootContextLoader::class, initializers = [BeansInitializer::class])
@CucumberContextConfiguration
@Import(value = [AcceptanceConfiguration.CucumberConfiguration::class])
@ActiveProfiles(profiles = ["test", "acceptance"])
open class AcceptanceConfiguration {

    @Configuration
    @Profile("acceptance")
    class CucumberConfiguration {

        @Bean
        fun repository(): InMemoryPersistence = InMemoryPersistence()
    }
}