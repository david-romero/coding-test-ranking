package com.idealista.domain.scoring

import com.idealista.domain.Ad

class DescriptionIsNotBlankRule : ScoreRule {

    override fun apply(ad: Ad): Int = if (ad.hasDescription()) 5 else 0
}