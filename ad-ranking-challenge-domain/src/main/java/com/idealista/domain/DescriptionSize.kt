package com.idealista.domain

enum class DescriptionSize {

    SMALL, BIG, UNDEFINED;

    companion object {
        fun valueOf(ad: Ad): DescriptionSize {
            return when (ad.typology) {
                Typology.FLAT -> {
                    return when {
                        IntRange(20, 49).contains(ad.description.words) -> SMALL
                        IntRange(50, Int.MAX_VALUE).contains(ad.description.words) -> BIG
                        else -> UNDEFINED
                    }
                }
                Typology.CHALET -> {
                    return if (ad.description.words > 50) {
                        BIG
                    } else {
                        SMALL
                    }
                }
                else -> UNDEFINED
            }
        }

    }

}