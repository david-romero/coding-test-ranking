package com.idealista.usecases

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isTrue
import assertk.assertions.prop
import com.idealista.domain.*
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
        adRepository.saveAll(listOf(
                ad {
                    id { "1" }
                    typology { Typology.CHALET }
                    houseSize { 300 }
                    score { 60 }
                },
                ad {
                    id { "2" }
                    typology { Typology.FLAT }
                    houseSize { 300 }
                    score { 60 }
                }))

        // when
        val response = showAds.execute(ShowAdsParams())

        // then
        assertThat(response)
                .isNotNull()
                .prop(Either<Either.Left<Validation>, Either.Right<Ads>>::isRight)
                .isTrue()
        assertThat(response.get())
                .isEqualTo(Ads(listOf(
                        Ad(StringBasedAdIdentifier("1"), Typology.CHALET, Description.empty(), emptyList(), 300, null, null, Score(60)),
                        Ad(StringBasedAdIdentifier("2"), Typology.FLAT, Description.empty(), emptyList(), 300, null, null, Score(60))
                )))
    }

    @Test
    fun `given three existing ads with different score when the ads are shown then ads are returned sorted by score descending`() {
        // given
        adRepository.saveAll(listOf(
                ad {
                    id { "1" }
                    typology { Typology.CHALET }
                    houseSize { 300 }
                    score { 40 }
                },
                ad {
                    id { "2" }
                    typology { Typology.FLAT }
                    houseSize { 300 }
                    score { 60 }
                },
                ad {
                    id { "3" }
                    typology { Typology.FLAT }
                    houseSize { 300 }
                    score { 45 }
                }))

        // when
        val response = showAds.execute(ShowAdsParams())

        // then
        assertThat(response)
                .isNotNull()
                .prop(Either<Either.Left<Validation>, Either.Right<String>>::isRight)
                .isTrue()
        assertThat(response.get())
                .isEqualTo(Ads(listOf(
                        Ad(StringBasedAdIdentifier("2"), Typology.FLAT, Description.empty(), emptyList(), 300, null, null, Score(60)),
                        Ad(StringBasedAdIdentifier("3"), Typology.FLAT, Description.empty(), emptyList(), 300, null, null, Score(45)),
                        Ad(StringBasedAdIdentifier("1"), Typology.CHALET, Description.empty(), emptyList(), 300, null, null, Score(40))
                )))
    }

    @Test
    fun `given three existing ads two of them are irrelevant when the ads are shown then only one ad is returned`() {
        // given
        adRepository.saveAll(listOf(
                ad {
                    id { "1" }
                    typology { Typology.CHALET }
                    score { 10 }
                },
                ad {
                    id { "2" }
                    typology { Typology.FLAT }
                    score { 40 }
                },
                ad {
                    id { "3" }
                    typology { Typology.FLAT }
                    score { 15 }
                }))


        // when
        val response = showAds.execute(ShowAdsParams())

        // then
        assertThat(response)
                .isNotNull()
                .prop(Either<Either.Left<Validation>, Either.Right<String>>::isRight)
                .isTrue()
        assertThat(response.get())
                .isEqualTo(Ads(listOf(
                        ad {
                            id { "2" }
                            typology { Typology.FLAT }
                            score { 40 }
                        }
                )))
    }
}