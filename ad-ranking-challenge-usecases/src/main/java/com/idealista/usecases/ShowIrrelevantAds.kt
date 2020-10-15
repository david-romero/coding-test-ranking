package com.idealista.usecases

import com.idealista.domain.Ad
import com.idealista.domain.AdRepository
import com.idealista.domain.IrrelevantAd
import com.idealista.domain.IrrelevantAds
import com.idealista.usecases.ad.params.ShowIrrelevantAdsParams
import com.idealista.usecases.shared.Either
import com.idealista.usecases.shared.UseCase
import com.idealista.usecases.shared.Validation
import java.time.OffsetDateTime
import java.time.ZoneOffset

class ShowIrrelevantAds(private val adRepository: AdRepository) : UseCase<ShowIrrelevantAdsParams, IrrelevantAds> {
    override fun execute(params: ShowIrrelevantAdsParams): Either<Validation, IrrelevantAds> {
        return IrrelevantAds(adRepository.findAll()
                .filter(Ad::isIrrelevant)
                .map(this::asIrrelevantAd)
                .sortedByDescending { it.since })
                .let { Either.Right(it) }
    }

    private fun asIrrelevantAd(it: Ad) = IrrelevantAd(it, getIrrelevantDate(it))

    private fun getIrrelevantDate(it: Ad) = it.irrelevantSince?.atOffset(ZoneOffset.of("+02:00")) ?: OffsetDateTime.now()
}