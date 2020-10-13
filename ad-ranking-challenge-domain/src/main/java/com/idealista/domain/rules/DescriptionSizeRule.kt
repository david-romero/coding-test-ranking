package com.idealista.domain.rules

import com.idealista.domain.Ad
import com.idealista.domain.Typology

class DescriptionSizeRule : ScoreRule {
    override fun apply(ad: Ad): Int {
        return when (ad.typology) {
            Typology.CHALET -> if (getWords(ad.description) >= 50) ad.score + 20 else ad.score
            else -> ad.score
        }
    }

    private fun getWords(text: String) = text.split(" ").size
}