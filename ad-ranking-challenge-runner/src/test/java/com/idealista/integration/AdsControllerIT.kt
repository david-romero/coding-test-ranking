package com.idealista.integration

import com.idealista.infrastructure.api.AdsController
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient


internal class AdsControllerIT {

    private val client = WebTestClient
            .bindToController(AdsController())
            .build()

    @Test
    fun `given a calculate score request when the HTTP PUT Request is received then a 204 no content is received`() {
        // given
        val request = null

        // when
        val response = client.put()
                .uri("/api/1/ad/score/calculate")
                .exchange()

        // then
        response.expectStatus().isNoContent
    }
}