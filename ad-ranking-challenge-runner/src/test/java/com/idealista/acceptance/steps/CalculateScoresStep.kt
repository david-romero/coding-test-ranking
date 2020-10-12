package com.idealista.acceptance.steps

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import com.idealista.acceptance.config.AcceptanceConfiguration
import com.idealista.acceptance.config.stubs.InMemoryPersistence
import com.idealista.infrastructure.persistence.AdVO
import com.idealista.usecases.score.params.CalculateScoresParams
import com.idealista.usecases.shared.UseCase
import io.cucumber.datatable.DataTable
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When

class CalculateScoresStep(val repository: InMemoryPersistence, val calculateScores: UseCase<CalculateScoresParams, Nothing>) : AcceptanceConfiguration() {

    @Given("the following adds")
    fun theFollowingAdds(data: DataTable) {
        data.asLists().drop(1)
                .map {
                    AdVO(it[0].toInt(), it[1].toString(), getDescription(it), getPictures(it), it[4].toInt(), null, null, null)
                }.forEach {
                    repository.ads.add(it)
                }
    }

    private fun getDescription(it: MutableList<String>) = it[2]?.toString()

    private fun getPictures(it: MutableList<String>): List<Int> {
        return if (it[3] != null) {
            listOf(it[3].toInt())
        } else {
            listOf()
        }
    }

    @When("the scores are calculated")
    fun theScoresAreCalculated() {
        calculateScores.execute(CalculateScoresParams())
    }

    @Then("the following scores are calculated")
    fun theFollowingScoresAreCalculated(data: DataTable) {
        data.asLists().drop(1)
                .forEach {
                    assertThat(getAd(it[0])).isNotNull().transform { AdVO::score }.isNotNull().isEqualTo(it[1].toInt())
                }
    }

    private fun getAd(it: String) {
        repository.ads.firstOrNull { ad ->
            ad.id == it[0].toInt()
        }
    }
}