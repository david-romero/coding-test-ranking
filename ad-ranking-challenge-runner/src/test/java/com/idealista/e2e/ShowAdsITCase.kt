package com.idealista.e2e

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import com.idealista.Main
import com.idealista.domain.Typology
import com.idealista.e2e.config.EndToEndConfiguration
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [EndToEndConfiguration::class, Main::class])
@ActiveProfiles("test")
internal class ShowAdsITCase(@Autowired val testRestTemplate: TestRestTemplate) {


    @Test
    fun `Given an ad list when the score is calculated then al score values are not null and greater than zero`() {
        // given
        val request = null
        testRestTemplate.put("/api/1/ad/score/calculate", request)

        // when
        val responseEntity = testRestTemplate.getForEntity("/api/1/ad", List::class.java)

        // then
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(responseEntity.body)
                .isNotNull()
                .isEqualTo(listOf(
                        mapOf("id" to 2, "typology" to Typology.FLAT.name, "description" to "Nuevo ático céntrico recién reformado. No deje pasar la oportunidad y adquiera este ático de lujo", "pictureUrls" to listOf("http://www.idealista.com/pictures/4"), "houseSize" to 300, "gardenSize" to null),
                        mapOf("id" to 4, "typology" to Typology.FLAT.name, "description" to "Ático céntrico muy luminoso y recién reformado, parece nuevo", "pictureUrls" to listOf("http://www.idealista.com/pictures/5"), "houseSize" to 300, "gardenSize" to null),
                        mapOf("id" to 5, "typology" to Typology.FLAT.name, "description" to "Pisazo,", "pictureUrls" to listOf("http://www.idealista.com/pictures/3", "http://www.idealista.com/pictures/8"), "houseSize" to 300, "gardenSize" to null)
                ))
    }

}