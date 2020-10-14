package com.idealista.integration

import com.idealista.domain.*
import com.idealista.domain.rules.Ads
import com.idealista.infrastructure.api.AdsController
import com.idealista.usecases.ad.params.ShowAdsParams
import com.idealista.usecases.score.params.CalculateScoresParams
import com.idealista.usecases.shared.Either
import com.idealista.usecases.shared.UseCase
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.springframework.test.web.reactive.server.WebTestClient


internal class AdsControllerIT {

    private val calculateScores: UseCase<CalculateScoresParams, Any> = mock()

    private val showAds: UseCase<ShowAdsParams, Ads> = mock()

    private val client = WebTestClient
            .bindToController(AdsController(calculateScores, showAds))
            .build()

    @Test
    fun `given a calculate score request when the HTTP PUT Request is received then a 204 no content is received`() {
        // given
        given(calculateScores.execute(CalculateScoresParams())).willReturn(Either.Right(""))

        // when
        val response = client.put()
                .uri("/api/1/ad/score/calculate")
                .exchange()

        // then
        response.expectStatus().isNoContent
    }

    @Test
    fun `given a show ads request when the HTTP GET Request is received then a 200 ok is received`() {
        // given
        given(showAds.execute(ShowAdsParams())).willReturn(Either.Right(Ads(listOf(
                Ad(IntBasedAdIdentifier(1), Typology.FLAT, "Piso muy bonito", listOf(Picture(IntBasedPictureIdentifier(1), "http://idealista.com/static/photos/1.jpg", Quality.HIGH_DEFINITION)), 300),
                Ad(IntBasedAdIdentifier(2), Typology.CHALET, "Chalet muy bonito", listOf(Picture(IntBasedPictureIdentifier(3), "http://idealista.com/static/photos/3.jpg", Quality.HIGH_DEFINITION)), 500, 100)
        ))))

        // when
        val response = client.get()
                .uri("/api/1/ad")
                .exchange()

        // then
        response.expectStatus().isOk
    }

    @Test
    fun `given a show ads request when the HTTP GET Request is received then the ads are received`() {
        // given
        given(showAds.execute(ShowAdsParams())).willReturn(Either.Right(Ads(listOf(
                Ad(IntBasedAdIdentifier(1), Typology.FLAT, "Piso muy bonito", listOf(Picture(IntBasedPictureIdentifier(1), "http://idealista.com/static/photos/1.jpg", Quality.HIGH_DEFINITION)), 300),
                Ad(IntBasedAdIdentifier(2), Typology.CHALET, "Chalet muy bonito", listOf(Picture(IntBasedPictureIdentifier(3), "http://idealista.com/static/photos/3.jpg", Quality.HIGH_DEFINITION)), 500, 100)
        ))))

        // when
        val response = client.get()
                .uri("/api/1/ad")
                .exchange()

        // then
        response.expectBody()
                .jsonPath("$").isArray
                .jsonPath("$.length()").isEqualTo(2)
                .jsonPath("$[0].id").isEqualTo(1)
                .jsonPath("$[0].typology").isEqualTo("FLAT")
                .jsonPath("$[0].description").isEqualTo("Piso muy bonito")
                .jsonPath("$[0].pictureUrls").isArray
                .jsonPath("$[0].pictureUrls.length()").isEqualTo(1)
                .jsonPath("$[0].pictureUrls[0]").isEqualTo("http://idealista.com/static/photos/1.jpg")
                .jsonPath("$[0].houseSize").isEqualTo(300)
                .jsonPath("$[1].id").isEqualTo(2)
                .jsonPath("$[1].typology").isEqualTo("CHALET")
                .jsonPath("$[1].description").isEqualTo("Chalet muy bonito")
                .jsonPath("$[1].pictureUrls").isArray
                .jsonPath("$[1].pictureUrls.length()").isEqualTo(1)
                .jsonPath("$[1].pictureUrls[0]").isEqualTo("http://idealista.com/static/photos/3.jpg")
                .jsonPath("$[1].houseSize").isEqualTo(500)
                .jsonPath("$[1].gardenSize").isEqualTo(100)
    }

    private inline fun <reified T : Any> mock(): T = Mockito.mock(T::class.java)
}

inline class IntBasedAdIdentifier(private val value: Int) : AdIdentifier {
    override fun toString(): String {
        return "$value"
    }
}

inline class IntBasedPictureIdentifier(private val value: Int) : PictureIdentifier {
    override fun toString(): String {
        return "$value"
    }
}
