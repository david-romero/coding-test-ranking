package com.idealista.domain.rules

import com.idealista.domain.Ad

class NoPicturesScoreRule : ScoreRule {
    override fun apply(ad: Ad): Int {
        return if (ad.pictures.isEmpty()) {
            -10
        } else {
            0
        }
    }
}