package com.idealista.usecases

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isTrue
import assertk.assertions.prop
import com.idealista.domain.Ad
import com.idealista.domain.Typology
import com.idealista.domain.rules.Ads
import com.idealista.usecases.ad.params.ShowAdsParams
import com.idealista.usecases.shared.Either
import com.idealista.usecases.shared.Validation
import com.idealista.usecases.stubs.AdRepositoryStub
import org.junit.jupiter.api.Test

internal class ShowAdsTest {

    private val adRepository = AdRepositoryStub()

    private val showAds = ShowAds(adRepository)

    @Test
    fun `given two existing ads into the repository when the ads are shown then the existing ads are returned`() {
        // given
        adRepository.save(Ad(StringBasedAdIdentifier("1"), Typology.CHALET, "", emptyList(), 300))
        adRepository.save(Ad(StringBasedAdIdentifier("2"), Typology.FLAT, "", emptyList(), 300))


        // when
        val response = showAds.execute(ShowAdsParams())

        // then
        assertThat(response)
                .isNotNull()
                .prop(Either<Either.Left<Validation>, Either.Right<String>>::isRight)
                .isTrue()
        assertThat(response.get())
                .isEqualTo(Ads(listOf(
                        Ad(StringBasedAdIdentifier("1"), Typology.CHALET, "", emptyList(), 300),
                        Ad(StringBasedAdIdentifier("2"), Typology.FLAT, "", emptyList(), 300)
                )))
    }
}