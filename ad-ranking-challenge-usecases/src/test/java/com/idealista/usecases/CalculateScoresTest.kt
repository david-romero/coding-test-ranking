package com.idealista.usecases

import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.index
import assertk.assertions.isEqualTo
import com.idealista.domain.Ad
import com.idealista.domain.AdIdentifier
import com.idealista.domain.Typology
import com.idealista.usecases.score.params.CalculateScoresParams
import com.idealista.usecases.stubs.AdRepositoryStub
import org.junit.jupiter.api.Test

internal class CalculateScoresTest {

    private val adRepository = AdRepositoryStub()

    private val calculateScores = CalculateScores(adRepository)

    @Test
    fun `given an existing ad without pictures when the score is calculated then the -10 is set as score`() {
        // given
        adRepository.save(Ad(StringBasedAdIdentifier("1"), Typology.CHALET, "Este piso es una ganga, compra, compra, COMPRA!!!!!", emptyList(), 300, null, null))

        // when
        calculateScores.execute(CalculateScoresParams())

        // then
        assertThat(adRepository.findAll()).hasSize(1)
        assertThat(adRepository.findAll()).index(0).transform(transform = Ad::score).isEqualTo(-10)
    }
}

inline class StringBasedAdIdentifier(val value: String) : AdIdentifier