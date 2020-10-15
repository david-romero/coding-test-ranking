package com.idealista.acceptance.steps

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.prop
import com.idealista.acceptance.config.AcceptanceConfiguration
import com.idealista.acceptance.config.stubs.InMemoryPersistence
import com.idealista.domain.Ad
import com.idealista.domain.Score
import com.idealista.infrastructure.persistence.AdVO
import com.idealista.usecases.score.params.CalculateScoresParams
import com.idealista.usecases.shared.UseCase
import io.cucumber.datatable.DataTable
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import java.time.OffsetDateTime

class CalculateScoresStep(
        private val adRepository: InMemoryPersistence,
        private val calculateScores: UseCase<CalculateScoresParams, Any>) : AcceptanceConfiguration() {

    @Given("the following adds")
    fun theFollowingAdds(data: DataTable) {
        data.asLists().drop(1)
                .map {
                    AdVO(it[0].toInt(), it[1].toString(), getDescription(it), getPictures(it), it[4].toInt(), null, null, getIrrelevantDate(it))
                }.forEach {
                    adRepository.database[it.id.toString()] = it
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
                    assertThat(getAd(it[0])).isNotNull().prop(Ad::score).prop(Score::points).isNotNull().isEqualTo(it[1].toInt())
                }
    }

    private fun getIrrelevantDate(it: MutableList<String>) =
            if (it.size == 6 && it[5] != null) OffsetDateTime.parse(it[5]).toInstant().toEpochMilli() else null

    private fun getDescription(notMappedAdd: MutableList<String?>) = notMappedAdd[2] ?: ""

    private fun getPictures(notMappedAdd: MutableList<String?>): List<Int> {
        return if (notMappedAdd[3] != null) {
            listOf(notMappedAdd[3]?.toInt() ?: 0)
        } else {
            listOf()
        }
    }

    private fun getAd(it: String): Ad? {
        return adRepository.findAll().firstOrNull { ad ->
            ad.id.toString() == it[0].toString()
        }
    }
}