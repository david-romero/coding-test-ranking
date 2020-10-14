package com.idealista.e2e

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import com.idealista.Main
import com.idealista.domain.Typology
import com.idealista.e2e.config.EndToEndConfiguration
import com.idealista.infrastructure.api.PublicAd
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

        // when
        val responseEntity = testRestTemplate.getForEntity("/api/1/ad", Any::class.java)

        // then
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(responseEntity.body)
                .isNotNull()
                .isEqualTo(listOf(
                        PublicAd(id = 2, typology = Typology.FLAT.name, description = "Nuevo ático céntrico recién reformado. No deje pasar la oportunidad y adquiera este ático de lujo", pictureUrls = listOf("4"), houseSize = 300, gardenSize = null),
                        PublicAd(id = 4, typology = Typology.FLAT.name, description = "Ático céntrico muy luminoso y recién reformado, parece nuevo", pictureUrls = listOf("5"), houseSize = 300, gardenSize = null),
                        PublicAd(id = 5, typology = Typology.FLAT.name, description = "Pisazo,", pictureUrls = listOf("3", "8"), houseSize = 300, gardenSize = null),
                        PublicAd(id = 8, typology = Typology.CHALET.name, description = "Maravilloso chalet situado en lAs afueras de un pequeño pueblo rural. El entorno es espectacular, las vistas magníficas. ¡Cómprelo ahora!", pictureUrls = listOf("1", "7"), houseSize = 300, gardenSize = null),
                        PublicAd(id = 3, typology = Typology.CHALET.name, description = "", pictureUrls = listOf("2"), houseSize = 300, gardenSize = null),
                        PublicAd(id = 6, typology = Typology.GARAGE.name, description = "", pictureUrls = listOf("6"), houseSize = 300, gardenSize = null),
                        PublicAd(id = 1, typology = Typology.CHALET.name, description = "Este piso es una ganga, compra, compra, COMPRA!!!!!", pictureUrls = listOf(), houseSize = 300, gardenSize = null),
                        PublicAd(id = 7, typology = Typology.GARAGE.name, description = "Garaje en el centro de Albacete", pictureUrls = listOf(), houseSize = 300, gardenSize = null)
                ))
    }

}