package com.idealista.domain.scoring

import com.idealista.domain.Ad

class NoPicturesScoreRule : ScoreRule {
    override fun apply(ad: Ad) = if (ad.hasNoPictures()) -10 else 0
}