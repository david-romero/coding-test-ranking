package com.idealista.acceptance.steps

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isTrue
import assertk.assertions.prop
import com.idealista.acceptance.config.World
import com.idealista.domain.Ad
import com.idealista.domain.AdRepository
import com.idealista.domain.rules.Ads
import com.idealista.usecases.ad.params.ShowAdsParams
import com.idealista.usecases.shared.Either
import com.idealista.usecases.shared.Either.Left
import com.idealista.usecases.shared.Either.Right
import com.idealista.usecases.shared.UseCase
import com.idealista.usecases.shared.Validation
import io.cucumber.datatable.DataTable
import io.cucumber.java.en.Then
import io.cucumber.java.en.When

class ShowAdsStep(
        private val showAds: UseCase<ShowAdsParams, Ads>,
        private val world: World,
        private val adRepository: AdRepository) {

    @When("ads are shown")
    fun adsAreShown() {
        world.ads = showAds.execute(ShowAdsParams())
    }

    @Then("the following ads are shown")
    fun theFollowingScoresAdsAreShown(data: DataTable) {
        assertThat(world.ads)
                .isNotNull()
                .prop(Either<Left<Validation>, Right<String>>::isRight)
                .isTrue()
        data.asLists().drop(1)
                .mapNotNull {
                    getAd(it[0])
                }
                .run {
                    assertThat(this).isEqualTo(world.ads?.get()?.publicAds)
                }
    }

    @Then("ads are sorted by score descending")
    fun adsAreSortedByScoreDescending() {
        assertThat(world.ads)
                .isNotNull()
                .prop(Either<Left<Validation>, Right<String>>::isRight)
                .isTrue()
        assertThat(world.ads?.get())
                .isEqualTo(adsSortedByScore())
    }

    private fun adsSortedByScore() = world.ads?.get()?.publicAds?.sortedByDescending { it.score }

    private fun getAd(rawAd: String): Ad? = adRepository.findAll().firstOrNull { ad -> ad.id.toString() == rawAd[0].toString() }
}