package com.idealista.domain.rules

import com.idealista.domain.Ad
import com.idealista.domain.Quality

class QualityPictureRule : ScoreRule {
    override fun apply(ad: Ad): Int {
        return ad.pictures.map {
            when (it.quality) {
                Quality.HIGH_DEFINITION -> 20
                Quality.STANDARD_DEFINITION -> 10
                else -> 0
            }
        }.sum()
    }
}