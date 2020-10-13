package com.idealista.domain.rules

import com.idealista.domain.Ad
import com.idealista.domain.Typology

class AdIsCompleteRule : ScoreRule {
    override fun apply(ad: Ad): Int {
        if (ad.typology == Typology.CHALET) {
            if (!ad.description.isBlank() && ad.pictures.isNotEmpty() && ad.houseSize > 0 && ad.gardenSize ?: 0 > 0) {
                return ad.score + 40
            }
        }
        return ad.score
    }
}