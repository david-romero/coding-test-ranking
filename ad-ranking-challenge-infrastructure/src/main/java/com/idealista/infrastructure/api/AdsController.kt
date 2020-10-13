package com.idealista.infrastructure.api

import com.idealista.usecases.score.params.CalculateScoresParams
import com.idealista.usecases.shared.UseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AdsController(val calculateScores: UseCase<CalculateScoresParams, Any>) {

    fun qualityListing(): ResponseEntity<List<QualityAd>> {
        //TODO rellena el cuerpo del método
        return ResponseEntity.notFound().build()
    }

    //TODO añade url del endpoint
    fun publicListing(): ResponseEntity<List<PublicAd>> {
        //TODO rellena el cuerpo del método
        return ResponseEntity.notFound().build()
    }

    @PutMapping("/api/1/ad/score/calculate")
    fun calculateScore(): ResponseEntity<Any> {
        return calculateScores.execute(CalculateScoresParams()).fold({
            ResponseEntity.badRequest().body(it.getErrors().joinToString(","))
        }, {
            ResponseEntity.noContent().build()
        })
    }
}