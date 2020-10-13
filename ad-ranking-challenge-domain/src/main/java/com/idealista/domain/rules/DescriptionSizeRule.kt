package com.idealista.domain.rules

import com.idealista.domain.Ad
import com.idealista.domain.Typology

class DescriptionSizeRule : ScoreRule {
    override fun apply(ad: Ad): Int {
        val descriptionScore = TypologyDescriptionScore.valueOf(ad.typology)
        return if (descriptionScore.isInRange(getWords(ad.description))) ad.score + descriptionScore.getScore() else ad.score
    }

    private fun getWords(text: String) = text.split(" ").size

    enum class TypologyDescriptionScore {

        CHALET {
            override fun getLowerLimit(): Int = 50
            override fun getUpperLimit(): Int = Int.MAX_VALUE

            override fun getScore(): Int = 20
        },
        FLAT {
            override fun getLowerLimit(): Int = 20
            override fun getUpperLimit(): Int = 49

            override fun getScore(): Int = 10
        };

        abstract fun getLowerLimit(): Int

        abstract fun getUpperLimit(): Int

        abstract fun getScore(): Int

        fun isInRange(words: Int) = IntRange(getLowerLimit(), getUpperLimit()).contains(words)

        companion object {
            fun valueOf(typology: Typology) = when (typology) {
                Typology.FLAT -> FLAT
                else -> CHALET
            }
        }

    }
}