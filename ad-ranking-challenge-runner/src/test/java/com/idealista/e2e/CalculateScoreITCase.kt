package com.idealista.e2e

import assertk.assertThat
import assertk.assertions.isGreaterThan
import assertk.assertions.isNotNull
import com.idealista.Main
import com.idealista.e2e.config.EndToEndConfiguration
import com.idealista.infrastructure.persistence.InMemoryPersistence
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [EndToEndConfiguration::class, Main::class])
@ActiveProfiles("test")
internal class CalculateScoreITCase(@Autowired val testRestTemplate: TestRestTemplate, @Autowired val repository: InMemoryPersistence) {


    @Test
    fun `Given an ad list when the score is calculated then al score values are not null and greater than zero`() {
        // given
        val request = null

        // when
        testRestTemplate.put("/api/1/ad/score/calculate", request)

        // then
        repository.ads.forEach() {
            assertThat(it).isNotNull()
            assertThat(it.score)
                    .isNotNull()
                    .isGreaterThan(-10)
        }
    }

}