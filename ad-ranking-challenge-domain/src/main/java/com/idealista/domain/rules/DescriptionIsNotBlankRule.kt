package com.idealista.domain.rules

import com.idealista.domain.Ad

class DescriptionIsNotBlankRule : ScoreRule {
    override fun apply(ad: Ad): Int {
        return if (ad.description.isBlank()) {
            0
        } else {
            5
        }
    }
}