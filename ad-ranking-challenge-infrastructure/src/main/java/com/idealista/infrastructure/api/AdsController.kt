package com.idealista.infrastructure.api

import com.idealista.domain.Ad
import com.idealista.domain.Ads
import com.idealista.domain.IrrelevantAd
import com.idealista.domain.IrrelevantAds
import com.idealista.usecases.ad.params.ShowAdsParams
import com.idealista.usecases.ad.params.ShowIrrelevantAdsParams
import com.idealista.usecases.score.params.CalculateScoresParams
import com.idealista.usecases.shared.UseCase
import com.idealista.usecases.shared.Validation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController
import java.time.format.DateTimeFormatter

@RestController
class AdsController(
        val calculateScores: UseCase<CalculateScoresParams, Any>,
        val showAds: UseCase<ShowAdsParams, Ads>,
        val showIrrelevantAds: UseCase<ShowIrrelevantAdsParams, IrrelevantAds>
) {

    @GetMapping("/api/1/ad/irrelevant")
    fun qualityListing(): ResponseEntity<Any> {
        return showIrrelevantAds.execute(ShowIrrelevantAdsParams()).fold({
            badRequest(it)
        }, { ads ->
            ads.ads.map { ad ->
                mapToDto(ad)
            }.let {
                ResponseEntity.ok(it)
            }
        })
    }

    @GetMapping("/api/1/ad")
    fun publicListing(): ResponseEntity<Any> {
        return showAds.execute(ShowAdsParams()).fold({
            badRequest(it)
        }, { ads ->
            ads.publicAds.map { ad ->
                mapToDto(ad)
            }.let {
                ResponseEntity.ok(it)
            }
        })
    }

    @PutMapping("/api/1/ad/score/calculate")
    fun calculateScore(): ResponseEntity<Any> {
        return calculateScores.execute(CalculateScoresParams()).fold({
            badRequest(it)
        }, {
            ResponseEntity.noContent().build()
        })
    }

    private fun badRequest(it: Validation): ResponseEntity<Any> =
            ResponseEntity.badRequest().body(it.getErrors().joinToString(","))

    private fun mapToDto(ad: Ad) =
            PublicAd(ad.id.toString().toInt(), ad.typology.name, ad.description.content, ad.pictures.map { it.url }, ad.houseSize, ad.gardenSize)

    private fun mapToDto(irrelevantAd: IrrelevantAd) =
            QualityAd(irrelevantAd.ad.id.toString().toInt(), irrelevantAd.ad.typology.name, irrelevantAd.ad.description.content, irrelevantAd.ad.pictures.map { it.url }, irrelevantAd.ad.houseSize, irrelevantAd.ad.gardenSize, irrelevantAd.ad.score.points, irrelevantAd.since.format(DateTimeFormatter.ISO_DATE_TIME))
}