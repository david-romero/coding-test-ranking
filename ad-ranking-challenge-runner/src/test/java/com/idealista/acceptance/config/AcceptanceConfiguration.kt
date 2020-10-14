package com.idealista.acceptance.config

import com.idealista.acceptance.config.stubs.InMemoryPersistence
import com.idealista.domain.AdRepository
import com.idealista.domain.PictureRepository
import io.cucumber.spring.CucumberContextConfiguration
import org.springframework.boot.test.context.SpringBootContextLoader
import org.springframework.context.annotation.*
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(loader = SpringBootContextLoader::class)
@CucumberContextConfiguration
@Import(value = [AcceptanceConfiguration.CucumberConfiguration::class])
@ActiveProfiles(profiles = ["test", "acceptance"])
open class AcceptanceConfiguration {

    @Configuration
    @Profile("acceptance")
    class CucumberConfiguration {

        private val inMemoryPersistence = InMemoryPersistence()

        @Bean
        fun adRepository(): AdRepository = inMemoryPersistence

        @Bean
        @Primary
        fun pictureRepository(): PictureRepository = inMemoryPersistence
    }
}