package com.idealista.domain.scoring

import com.idealista.domain.Ad
import com.idealista.domain.DescriptionSize
import com.idealista.domain.Typology

class DescriptionSizeRule : ScoreRule {

    override fun apply(ad: Ad): Int = TypologyDescriptionScore.valueOf(ad.typology).apply(ad)

    private enum class TypologyDescriptionScore : ScoreRule {

        CHALET {
            override fun apply(ad: Ad): Int {
                return when (DescriptionSize.valueOf(ad)) {
                    DescriptionSize.BIG -> 20
                    else -> 0
                }
            }
        },
        FLAT {
            override fun apply(ad: Ad): Int {
                return when (DescriptionSize.valueOf(ad)) {
                    DescriptionSize.SMALL -> 10
                    DescriptionSize.BIG -> 30
                    else -> 0
                }
            }
        };

        companion object {
            fun valueOf(typology: Typology) = when (typology) {
                Typology.FLAT -> FLAT
                else -> CHALET
            }
        }
    }
}