package com.idealista.usecases

import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.index
import assertk.assertions.isEqualTo
import com.idealista.domain.Ad
import com.idealista.domain.AdIdentifier
import com.idealista.domain.Typology
import com.idealista.domain.rules.DescriptionIsNotBlankRule
import com.idealista.domain.rules.DescriptionSizeRule
import com.idealista.domain.rules.NoPicturesScoreRule
import com.idealista.domain.rules.QualityPictureRule
import com.idealista.usecases.score.params.CalculateScoresParams
import com.idealista.usecases.stubs.AdRepositoryStub
import com.idealista.usecases.stubs.IntBasedPictureIdentifier
import com.idealista.usecases.stubs.PictureRepositoryStub
import org.junit.jupiter.api.Test

internal class CalculateScoresTest {

    private val adRepository = AdRepositoryStub()

    private val pictureRepository = PictureRepositoryStub()

    private val calculateScores = CalculateScores(adRepository, listOf(NoPicturesScoreRule(), QualityPictureRule(pictureRepository), DescriptionIsNotBlankRule(), DescriptionSizeRule()))

    @Test
    fun `given an existing ad without pictures when the score is calculated then -10 is set as score`() {
        // given
        adRepository.save(Ad(StringBasedAdIdentifier("1"), Typology.CHALET, "", emptyList(), 300, null, null))

        // when
        calculateScores.execute(CalculateScoresParams())

        // then
        assertThat(adRepository.findAll()).hasSize(1)
        assertThat(adRepository.findAll()).index(0).transform(transform = Ad::score).isEqualTo(-10)
    }

    @Test
    fun `given an existing ad with a high resolution picture when the score is calculated then 20 is set as score`() {
        // given
        adRepository.save(Ad(StringBasedAdIdentifier("1"), Typology.CHALET, "", listOf(IntBasedPictureIdentifier(4)), 300, null, null))

        // when
        calculateScores.execute(CalculateScoresParams())

        // then
        assertThat(adRepository.findAll()).hasSize(1)
        assertThat(adRepository.findAll()).index(0).transform(transform = Ad::score).isEqualTo(20)
    }

    @Test
    fun `given an existing ad with a standard resolution picture when the score is calculated then 10 is set as score`() {
        // given
        adRepository.save(Ad(StringBasedAdIdentifier("1"), Typology.CHALET, "", listOf(IntBasedPictureIdentifier(1)), 300, null, null))

        // when
        calculateScores.execute(CalculateScoresParams())

        // then
        assertThat(adRepository.findAll()).hasSize(1)
        assertThat(adRepository.findAll()).index(0).transform(transform = Ad::score).isEqualTo(10)
    }

    @Test
    fun `given an existing ad with description when the score is calculated then 5 is added to the score`() {
        // given
        adRepository.save(Ad(StringBasedAdIdentifier("1"), Typology.CHALET, "Este piso es una ganga, compra, compra, COMPRA!!!!!", listOf(IntBasedPictureIdentifier(1)), 300, null, null))

        // when
        calculateScores.execute(CalculateScoresParams())

        // then
        assertThat(adRepository.findAll()).hasSize(1)
        assertThat(adRepository.findAll()).index(0).transform(transform = Ad::score).isEqualTo(15)
    }

    @Test
    fun `given an existing ad with a sixty words description of a Chalet when the score is calculated then 20 is added to the score`() {
        // given
        adRepository.save(Ad(StringBasedAdIdentifier("1"), Typology.CHALET, "This is a description with ten words eight nine ten ".repeat(6), listOf(IntBasedPictureIdentifier(1)), 300, null, null))

        // when
        calculateScores.execute(CalculateScoresParams())

        // then
        assertThat(adRepository.findAll()).hasSize(1)
        assertThat(adRepository.findAll()).index(0).transform(transform = Ad::score).isEqualTo(35)
    }

    @Test
    fun `given an existing ad with a thirty words description of a Flat when the score is calculated then 10 is added to the score`() {
        // given
        adRepository.save(Ad(StringBasedAdIdentifier("1"), Typology.FLAT, "This is a description with ten words eight nine ten ".repeat(3), listOf(IntBasedPictureIdentifier(1)), 300, null, null))

        // when
        calculateScores.execute(CalculateScoresParams())

        // then
        assertThat(adRepository.findAll()).hasSize(1)
        assertThat(adRepository.findAll()).index(0).transform(transform = Ad::score).isEqualTo(25)
    }

    @Test
    fun `given an existing ad with a sixty words description of a Flat when the score is calculated then 30 is added to the score`() {
        // given
        adRepository.save(Ad(StringBasedAdIdentifier("1"), Typology.FLAT, "This is a description with ten words eight nine ten ".repeat(6), listOf(IntBasedPictureIdentifier(1)), 300, null, null))

        // when
        calculateScores.execute(CalculateScoresParams())

        // then
        assertThat(adRepository.findAll()).hasSize(1)
        assertThat(adRepository.findAll()).index(0).transform(transform = Ad::score).isEqualTo(45)
    }
}

inline class StringBasedAdIdentifier(val value: String) : AdIdentifier