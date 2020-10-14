package com.idealista.infrastructure.di

import com.idealista.domain.Ad
import com.idealista.domain.rules.*
import com.idealista.usecases.CalculateScores
import com.idealista.usecases.ShowAds
import com.idealista.usecases.ad.params.ShowAdsParams
import com.idealista.usecases.score.params.CalculateScoresParams
import com.idealista.usecases.shared.UseCase
import org.springframework.context.support.beans


fun beans() = beans {
    bean<UseCase<CalculateScoresParams, Any>> {
        CalculateScores(ref(), provider<ScoreRule>().toList())
    }
    bean<UseCase<ShowAdsParams, Ads>> {
        ShowAds()
    }
    bean { KeyWordsDescriptionRule() }
    bean { NoPicturesScoreRule() }
    bean { QualityPictureRule(ref()) }
    bean { DescriptionSizeRule() }
    bean { DescriptionIsNotBlankRule() }
    bean { AdIsCompleteRule() }
}