package com.idealista.integration

import com.idealista.infrastructure.api.AdsController
import com.idealista.usecases.score.params.CalculateScoresParams
import com.idealista.usecases.shared.Either
import com.idealista.usecases.shared.UseCase
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.springframework.test.web.reactive.server.WebTestClient


internal class AdsControllerIT {

    private val calculateScores: UseCase<CalculateScoresParams, Any> = mock()

    private val client = WebTestClient
            .bindToController(AdsController(calculateScores))
            .build()

    @Test
    fun `given a calculate score request when the HTTP PUT Request is received then a 204 no content is received`() {
        // given
        given(calculateScores.execute(CalculateScoresParams())).willReturn(Either.Right(Any()))
        val path = "/api/1/ad/score/calculate"

        // when
        val response = client.put()
                .uri(path)
                .exchange()

        // then
        response.expectStatus().isNoContent
    }

    private inline fun <reified T : Any> mock(): T = Mockito.mock(T::class.java)
}