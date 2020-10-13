package com.idealista.domain.rules

import com.idealista.domain.Ad
import com.idealista.domain.Typology

class DescriptionSizeRule : ScoreRule {
    override fun apply(ad: Ad): Int {
        val descriptionScore = TypologyDescriptionScore.valueOf(ad.typology)
        return descriptionScore.apply(ad)
    }

    enum class TypologyDescriptionScore : ScoreRule {

        CHALET {
            override fun getLowerLimit(): Int = 50
            override fun getUpperLimit(): Int = Int.MAX_VALUE

            override fun getScore(): Int = 20

        },
        FLAT {
            override fun getLowerLimit(): Int = 20
            override fun getUpperLimit(): Int = 49

            override fun getScore(): Int = 10

            override fun apply(ad: Ad): Int {
                return when {
                    getWords(ad.description) >= 50 -> ad.score + 30
                    else -> super.apply(ad)
                }
            }

        };

        override fun apply(ad: Ad): Int = if (isInRange(getWords(ad.description))) ad.score + getScore() else ad.score

        abstract fun getLowerLimit(): Int

        abstract fun getUpperLimit(): Int

        abstract fun getScore(): Int

        private fun isInRange(words: Int) = IntRange(getLowerLimit(), getUpperLimit()).contains(words)

        fun getWords(text: String) = text.split(" ").size

        companion object {
            fun valueOf(typology: Typology) = when (typology) {
                Typology.FLAT -> FLAT
                else -> CHALET
            }
        }

    }
}