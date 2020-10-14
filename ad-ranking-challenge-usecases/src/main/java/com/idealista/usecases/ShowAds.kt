package com.idealista.usecases

import com.idealista.domain.AdRepository
import com.idealista.domain.rules.Ads
import com.idealista.usecases.ad.params.ShowAdsParams
import com.idealista.usecases.shared.Either
import com.idealista.usecases.shared.UseCase
import com.idealista.usecases.shared.Validation

class ShowAds(private val adRepository: AdRepository) : UseCase<ShowAdsParams, Ads> {
    override fun execute(params: ShowAdsParams): Either<Validation, Ads> {
        return Ads(adRepository.findAll()
                .filter {
                    it.score >= 40
                }
                .sortedByDescending { it.score }).let {
            Either.Right(it)
        }
    }
}