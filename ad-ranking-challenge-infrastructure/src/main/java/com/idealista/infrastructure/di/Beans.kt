package com.idealista.infrastructure.di

import com.idealista.domain.AdRepository
import com.idealista.domain.PictureRepository
import com.idealista.domain.rules.NoPicturesScoreRule
import com.idealista.domain.rules.QualityPictureRule
import com.idealista.usecases.CalculateScores
import com.idealista.usecases.score.params.CalculateScoresParams
import com.idealista.usecases.shared.UseCase
import org.springframework.context.support.beans


fun beans() = beans {
    bean<UseCase<CalculateScoresParams, Any>>() {
        CalculateScores(ref<AdRepository>(), listOf(NoPicturesScoreRule(), QualityPictureRule(ref<PictureRepository>("pictureRepository"))))
    }
}