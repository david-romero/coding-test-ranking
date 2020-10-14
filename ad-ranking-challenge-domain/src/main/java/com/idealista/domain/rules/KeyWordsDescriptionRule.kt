package com.idealista.domain.rules

import com.idealista.domain.Ad

class KeyWordsDescriptionRule : ScoreRule {

    override fun apply(ad: Ad): Int = ad.description.keyWordsOccurrences * 5
}