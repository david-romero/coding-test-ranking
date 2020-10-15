package com.idealista.infrastructure.di

import com.idealista.domain.Ads
import com.idealista.domain.IrrelevantAds
import com.idealista.domain.rules.*
import com.idealista.usecases.CalculateScores
import com.idealista.usecases.ShowAds
import com.idealista.usecases.ShowIrrelevantAds
import com.idealista.usecases.ad.params.ShowAdsParams
import com.idealista.usecases.ad.params.ShowIrrelevantAdsParams
import com.idealista.usecases.score.params.CalculateScoresParams
import com.idealista.usecases.shared.UseCase
import org.springframework.context.support.beans


fun beans() = beans {
    bean<UseCase<CalculateScoresParams, Any>> {
        CalculateScores(ref(), provider<ScoreRule>().toList())
    }
    bean<UseCase<ShowAdsParams, Ads>> {
        ShowAds(ref())
    }
    bean<UseCase<ShowIrrelevantAdsParams, IrrelevantAds>> {
        ShowIrrelevantAds(ref())
    }
    bean { KeyWordsDescriptionRule() }
    bean { NoPicturesScoreRule() }
    bean { QualityPictureRule() }
    bean { DescriptionSizeRule() }
    bean { DescriptionIsNotBlankRule() }
    bean { AdIsCompleteRule() }
}