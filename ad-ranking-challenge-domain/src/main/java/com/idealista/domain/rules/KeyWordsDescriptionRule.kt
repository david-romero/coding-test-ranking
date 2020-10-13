package com.idealista.domain.rules

import com.idealista.domain.Ad

class KeyWordsDescriptionRule : ScoreRule {

    private val pattern = "(luminoso)|(nuevo)|(céntrico)|(reformado)|(ático)+".toRegex().toPattern()

    override fun apply(ad: Ad): Int {
        return getOccurrences(ad.description) * 5 + ad.score
    }

    private fun getOccurrences(description: String): Int {
        val matcher = pattern.matcher(description.toLowerCase())
        var occurrences = 0
        while (matcher.find())
            occurrences++
        return occurrences
    }


}