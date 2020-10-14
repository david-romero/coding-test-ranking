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
                        mapOf("id" to 2, "typology" to Typology.FLAT.name, "description" to "Nuevo ático céntrico recién reformado. No deje pasar la oportunidad y adquiera este ático de lujo", "pictureUrls" to listOf("4"), "houseSize" to 300, "gardenSize" to null),
                        mapOf("id" to 4, "typology" to Typology.FLAT.name, "description" to "Ático céntrico muy luminoso y recién reformado, parece nuevo", "pictureUrls" to listOf("5"), "houseSize" to 300, "gardenSize" to null),
                        mapOf("id" to 5, "typology" to Typology.FLAT.name, "description" to "Pisazo,", "pictureUrls" to listOf("3", "8"), "houseSize" to 300, "gardenSize" to null),
                        mapOf("id" to 8, "typology" to Typology.CHALET.name, "description" to "Maravilloso chalet situado en lAs afueras de un pequeño pueblo rural. El entorno es espectacular, las vistas magníficas. ¡Cómprelo ahora!", "pictureUrls" to listOf("1", "7"), "houseSize" to 300, "gardenSize" to null),
                        mapOf("id" to 3, "typology" to Typology.CHALET.name, "description" to "", "pictureUrls" to listOf("2"), "houseSize" to 300, "gardenSize" to null),
                        mapOf("id" to 6, "typology" to Typology.GARAGE.name, "description" to "", "pictureUrls" to listOf("6"), "houseSize" to 300, "gardenSize" to null),
                        mapOf("id" to 1, "typology" to Typology.CHALET.name, "description" to "Este piso es una ganga, compra, compra, COMPRA!!!!!", "pictureUrls" to listOf<String>(), "houseSize" to 300, "gardenSize" to null),
                        mapOf("id" to 7, "typology" to Typology.GARAGE.name, "description" to "Garaje en el centro de Albacete", "pictureUrls" to listOf<String>(), "houseSize" to 300, "gardenSize" to null)
                ))
    }

}