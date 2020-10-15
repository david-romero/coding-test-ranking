package com.idealista.acceptance.steps

import assertk.assertThat
import assertk.assertions.*
import com.idealista.acceptance.config.World
import com.idealista.domain.AdRepository
import com.idealista.domain.IrrelevantAd
import com.idealista.domain.IrrelevantAds
import com.idealista.usecases.ad.params.ShowIrrelevantAdsParams
import com.idealista.usecases.shared.Either
import com.idealista.usecases.shared.UseCase
import com.idealista.usecases.shared.Validation
import io.cucumber.datatable.DataTable
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import java.time.OffsetDateTime
import java.time.ZoneOffset

class ShowIrrelevantAdsStep(private val showIrrelevantAds: UseCase<ShowIrrelevantAdsParams, IrrelevantAds>,
                            private val world: World,
                            private val adRepository: AdRepository) {


    @When("irrelevant ads are shown")
    fun irrelevantAdsAreShown() {
        world.irrelevantAds = showIrrelevantAds.execute(ShowIrrelevantAdsParams())
    }

    @Then("the following ads are returned")
    fun theFollowingAdsAreReturned(data: DataTable) {
        assertThat(world.irrelevantAds)
                .isNotNull()
                .prop(Either<Either.Left<Validation>, Either.Right<String>>::isRight)
                .isTrue()
        data.asLists().drop(1)
                .mapNotNull {
                    getAd(it[0])
                }
                .run {
                    forEach {
                        val actualIrrelevantAd = world.irrelevantAds?.let { either ->
                            either.get().ads.firstOrNull { irrelevantAd ->
                                irrelevantAd.ad.id == it.ad.id
                            }
                        }
                        assertThat(actualIrrelevantAd).isNotNull().isEqualToIgnoringGivenProperties(it, IrrelevantAd::since)
                    }
                }
    }

    @Then("irrelevant ads are sorted by the date in which the ad became irrelevant")
    fun irrelevantAdsAreSortedByTheDateInWhichTheAdBecameIrrelevant() {
        assertThat(world.irrelevantAds)
                .isNotNull()
                .prop(Either<Either.Left<Validation>, Either.Right<String>>::isRight)
                .isTrue()
        assertThat(world.irrelevantAds?.get())
                .isEqualTo(adsSortedByScore())
    }

    @Then("the attribute irrelevantDate is not null for each ad")
    fun `the attribute irrelevantDate is not null for each ad`() {
        assertThat(world.irrelevantAds)
                .isNotNull()
                .prop(Either<Either.Left<Validation>, Either.Right<String>>::isRight)
                .isTrue()
        world.irrelevantAds?.let {
            it.get().ads.forEach { irrelevantAd ->
                assertThat(irrelevantAd).prop(IrrelevantAd::since).isNotNull().matchesPredicate { date -> OffsetDateTime.MIN.isBefore(date) }
            }
        }
    }


    private fun adsSortedByScore() = IrrelevantAds(world.irrelevantAds?.get()?.ads?.sortedByDescending { it.since } ?: listOf())

    private fun getAd(rawAd: String): IrrelevantAd? {
        return adRepository.findAll()
                .first { ad -> ad.id.toString() == rawAd[0].toString() }
                .let {
                    IrrelevantAd(it, it.irrelevantSince?.atOffset(ZoneOffset.of("+02:00")) ?: OffsetDateTime.now())
                }
    }

}

