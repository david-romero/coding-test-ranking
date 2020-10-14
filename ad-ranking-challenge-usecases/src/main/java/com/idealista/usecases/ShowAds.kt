package com.idealista.usecases

import com.idealista.domain.Ad
import com.idealista.domain.AdRepository
import com.idealista.domain.Ads
import com.idealista.usecases.ad.params.ShowAdsParams
import com.idealista.usecases.shared.Either
import com.idealista.usecases.shared.UseCase
import com.idealista.usecases.shared.Validation

class ShowAds(private val adRepository: AdRepository) : UseCase<ShowAdsParams, Ads> {
    override fun execute(params: ShowAdsParams): Either<Validation, Ads> {
        return Ads(adRepository.findAll()
                .filter(Ad::isRelevant)
                .sortedByDescending { it.score.points })
                .let { Either.Right(it) }
    }
}