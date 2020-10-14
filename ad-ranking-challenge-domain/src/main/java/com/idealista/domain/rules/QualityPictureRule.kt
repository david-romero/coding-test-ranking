package com.idealista.domain.rules

import com.idealista.domain.Ad
import com.idealista.domain.PictureRepository
import com.idealista.domain.Quality

class QualityPictureRule(private val pictureRepository: PictureRepository) : ScoreRule {
    override fun apply(ad: Ad): Int {
        return ad.pictures.map {
            if (pictureRepository.findByIdentifier(it)?.quality == Quality.HIGH_DEFINITION) {
                20
            } else {
                10
            }
        }.sum()
    }
}