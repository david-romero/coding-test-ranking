package com.idealista.domain.rules

import com.idealista.domain.Ad

class AdIsCompleteRule : ScoreRule {

    override fun apply(ad: Ad) = if (ad.isComplete()) 40 else 0

}