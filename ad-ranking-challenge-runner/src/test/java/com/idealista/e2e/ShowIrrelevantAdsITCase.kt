package com.idealista.e2e

import assertk.Assert
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import com.idealista.Main
import com.idealista.domain.Typology
import com.idealista.e2e.config.EndToEndConfiguration
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [EndToEndConfiguration::class, Main::class])
@ActiveProfiles("test")
internal class ShowIrrelevantAdsITCase(@Autowired val testRestTemplate: TestRestTemplate) {


    @Test
    fun `Given an ad list with the score calculated when the irrelevant ads are shown then all irrelevant ads are returned`() {
        // given
        val request = null
        testRestTemplate.put("/api/1/ad/score/calculate", request)

        // when
        val responseEntity = testRestTemplate.getForEntity("/api/1/ad/irrelevant", List::class.java)

        // then
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(responseEntity.body)
                .isNotNull()
                .isEqualIgnoringPropertyInAnyOrder(listOf(
                        mapOf("id" to 7, "typology" to Typology.GARAGE.name, "description" to "Garaje en el centro de Albacete", "pictureUrls" to listOf<String>(), "houseSize" to 300, "gardenSize" to null, "score" to -5),
                        mapOf("id" to 1, "typology" to Typology.CHALET.name, "description" to "Este piso es una ganga, compra, compra, COMPRA!!!!!", "pictureUrls" to listOf<String>(), "houseSize" to 300, "gardenSize" to null, "score" to -5),
                        mapOf("id" to 3, "typology" to Typology.CHALET.name, "description" to "", "pictureUrls" to listOf("http://www.idealista.com/pictures/2"), "houseSize" to 300, "gardenSize" to null, "score" to 20),
                        mapOf("id" to 8, "typology" to Typology.CHALET.name, "description" to "Maravilloso chalet situado en lAs afueras de un pequeño pueblo rural. El entorno es espectacular, las vistas magníficas. ¡Cómprelo ahora!", "pictureUrls" to listOf("http://www.idealista.com/pictures/1", "http://www.idealista.com/pictures/7"), "houseSize" to 300, "gardenSize" to null, "score" to 25)
                ), "irrelevantSince")
    }

    fun <T> Assert<T>.isEqualIgnoringPropertyInAnyOrder(expected: List<Map<String, *>>, property: String) = given { actual ->
        if (actual is List<*>) {
            @Suppress("UNCHECKED_CAST") val actualList: List<Map<String, *>> = actual as List<Map<String, *>>
            actualList.forEach { itemMap ->
                val itemMutableMap = HashMap(itemMap)
                itemMutableMap.remove(property)
                val newMap = expected.first {
                    it["id"] == itemMutableMap["id"]
                }
                assertThat(itemMutableMap).isEqualInAnyOrder(newMap)
            }

        } else {
            fail(ClassCastException("""Expected a List actual is ${actual!!::class.simpleName}"""))
        }
    }

    private fun Assert<Map<String, *>>.isEqualInAnyOrder(expected: Map<String, *>) = given { actual ->
        actual.forEach {
            if (it.value != null) {
                assertThat(expected[it.key]).isNotNull().isEqualTo(it.value)
            } else {
                assertThat(expected[it.key]).isNull()
            }
        }
    }
}