package com.idealista.usecases

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isTrue
import assertk.assertions.prop
import com.idealista.domain.*
import com.idealista.usecases.ad.params.ShowIrrelevantAdsParams
import com.idealista.usecases.shared.Either
import com.idealista.usecases.shared.Validation
import com.idealista.usecases.stubs.AdRepositoryStub
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime
import java.util.*

internal class ShowIrrelevantAdsTest {

    private val adRepository = AdRepositoryStub()

    private val showIrrelevantAds = ShowIrrelevantAds(adRepository)

    @Test
    fun `given two ads once of them is irrelevant when irrelevant ads are shown then the unique irrelevant ad is returned`() {
        // given
        adRepository.save(Ad(StringBasedAdIdentifier("1"), Typology.CHALET, Description.empty(), emptyList(), 300, null, null, Score(60)))
        adRepository.save(Ad(StringBasedAdIdentifier("2"), Typology.FLAT, Description.empty(), emptyList(), 300, null, Date.from(OffsetDateTime.parse("2020-10-14T19:42:00+02:00").toInstant()), Score(30)))

        // when
        val response = showIrrelevantAds.execute(ShowIrrelevantAdsParams())

        // then
        assertThat(response)
                .isNotNull()
                .prop(Either<Either.Left<Validation>, Either.Right<IrrelevantAds>>::isRight)
                .isTrue()
        assertThat(response.get())
                .isEqualTo(IrrelevantAds(listOf(
                        IrrelevantAd(Ad(StringBasedAdIdentifier("2"), Typology.FLAT, Description.empty(), emptyList(), 300, null, Date.from(OffsetDateTime.parse("2020-10-14T19:42:00+02:00").toInstant()), Score(30)), OffsetDateTime.parse("2020-10-14T19:42:00+02:00"))
                )))
    }
}