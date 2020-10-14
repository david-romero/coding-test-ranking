package com.idealista.domain.rules

import com.idealista.domain.Ad
import com.idealista.domain.Typology
import com.idealista.domain.rules.AdIsCompleteRule.TypologyCompleteAdScore.Companion.valueOf

class AdIsCompleteRule : ScoreRule {
    override fun apply(ad: Ad): Int {
        if (adIsComplete(ad)) {
            return 40
        }
        return 0
    }

    private fun adIsComplete(ad: Ad) = valueOf(ad.typology).getPredicate().invoke(ad)

    private enum class TypologyCompleteAdScore {

        CHALET {
            override fun getPredicate(): Ad.() -> Boolean {
                return {
                    !description.isBlank() &&
                            pictures.isNotEmpty() &&
                            houseSize > 0 &&
                            gardenSize ?: 0 > 0
                }
            }
        },
        FLAT {
            override fun getPredicate(): Ad.() -> Boolean {
                return {
                    !description.isBlank() &&
                            pictures.isNotEmpty() &&
                            houseSize > 0
                }
            }
        };

        abstract fun getPredicate(): Ad.() -> Boolean

        companion object {
            fun valueOf(typology: Typology) = when (typology) {
                Typology.FLAT -> FLAT
                else -> CHALET
            }
        }

    }
}