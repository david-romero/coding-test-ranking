package com.idealista.domain.rules

import com.idealista.domain.Ad

class KeyWordsDescriptionRule : ScoreRule {

    private val keyWords = listOf("luminoso", "nuevo", "céntrico", "reformado", "ático")

    override fun apply(ad: Ad): Int {
        return getOccurrences(ad.description) * 5 + ad.score
    }

    private fun getOccurrences(description: String): Int {
        return keyWords.filter {
            description.toLowerCase().contains(it)
        }.count()
    }


}