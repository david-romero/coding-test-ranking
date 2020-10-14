package com.idealista.infrastructure.api

import com.idealista.domain.rules.Ads
import com.idealista.usecases.ad.params.ShowAdsParams
import com.idealista.usecases.score.params.CalculateScoresParams
import com.idealista.usecases.shared.UseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AdsController(
        val calculateScores: UseCase<CalculateScoresParams, Any>,
        val showAds: UseCase<ShowAdsParams, Ads>
) {

    fun qualityListing(): ResponseEntity<List<QualityAd>> {
        //TODO rellena el cuerpo del método
        return ResponseEntity.notFound().build()
    }

    @GetMapping("/api/1/ad")
    fun publicListing(): ResponseEntity<Any> {
        return showAds.execute(ShowAdsParams()).fold({
            ResponseEntity.badRequest().body(it.getErrors().joinToString(","))
        }, { ads ->
            ads.publicAds.map { ad ->
                PublicAd(ad.id.toString().toInt(), ad.typology.name, ad.description, ad.pictures.map { it.toString() }, ad.houseSize, ad.gardenSize)
            }.let {
                ResponseEntity.ok(it)
            }
        })
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