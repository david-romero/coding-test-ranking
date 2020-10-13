package com.idealista.acceptance.config

import com.idealista.acceptance.config.stubs.InMemoryPersistence
import com.idealista.domain.AdRepository
import com.idealista.domain.PictureRepository
import com.idealista.domain.rules.*
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

        private val inMemoryPersistence = InMemoryPersistence()

        @Bean
        fun adRepository(): AdRepository = inMemoryPersistence

        @Bean
        fun pictureRepository(): PictureRepository = inMemoryPersistence

        @Bean
        fun scoreRules(): List<ScoreRule> = listOf(NoPicturesScoreRule(), QualityPictureRule(inMemoryPersistence), DescriptionIsNotBlankRule(), DescriptionSizeRule(), KeyWordsDescriptionRule())


    }
}