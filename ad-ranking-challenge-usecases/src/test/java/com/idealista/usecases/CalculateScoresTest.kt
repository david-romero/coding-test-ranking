package com.idealista.usecases

import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.index
import assertk.assertions.isEqualTo
import com.idealista.domain.Ad
import com.idealista.domain.AdIdentifier
import com.idealista.domain.Score
import com.idealista.domain.Typology
import com.idealista.domain.rules.*
import com.idealista.usecases.score.params.CalculateScoresParams
import com.idealista.usecases.stubs.AdRepositoryStub
import com.idealista.usecases.stubs.IntBasedPictureIdentifier
import com.idealista.usecases.stubs.PictureRepositoryStub
import org.junit.jupiter.api.Test

internal class CalculateScoresTest {

    private val adRepository = AdRepositoryStub()

    private val pictureRepository = PictureRepositoryStub()

    private val calculateScores = CalculateScores(adRepository, listOf(NoPicturesScoreRule(), QualityPictureRule(pictureRepository), DescriptionIsNotBlankRule(), DescriptionSizeRule(), KeyWordsDescriptionRule(), AdIsCompleteRule()))

    @Test
    fun `given an existing ad without pictures when the score is calculated then -10 is set as score`() {
        // given
        adRepository.save(Ad(StringBasedAdIdentifier("1"), Typology.CHALET, "", emptyList(), 300))

        // when
        calculateScores.execute(CalculateScoresParams())

        // then
        assertThat(adRepository.findAll()).hasSize(1)
        assertThat(adRepository.findAll()).index(0).transform(transform = Ad::score).transform(transform = Score::points).isEqualTo(-10)
    }

    @Test
    fun `given an existing ad with a high resolution picture when the score is calculated then 20 is set as score`() {
        // given
        adRepository.save(Ad(StringBasedAdIdentifier("1"), Typology.CHALET, "", listOf(IntBasedPictureIdentifier(4)), 300))

        // when
        calculateScores.execute(CalculateScoresParams())

        // then
        assertThat(adRepository.findAll()).hasSize(1)
        assertThat(adRepository.findAll()).index(0).transform(transform = Ad::score).transform(transform = Score::points).isEqualTo(20)
    }

    @Test
    fun `given an existing ad with a standard resolution picture when the score is calculated then 10 is set as score`() {
        // given
        adRepository.save(Ad(StringBasedAdIdentifier("1"), Typology.CHALET, "", listOf(IntBasedPictureIdentifier(1)), 300))

        // when
        calculateScores.execute(CalculateScoresParams())

        // then
        assertThat(adRepository.findAll()).hasSize(1)
        assertThat(adRepository.findAll()).index(0).transform(transform = Ad::score).transform(transform = Score::points).isEqualTo(10)
    }

    @Test
    fun `given an existing ad with description when the score is calculated then 5 is added to the score`() {
        // given
        adRepository.save(Ad(StringBasedAdIdentifier("1"), Typology.CHALET, "Este piso es una ganga, compra, compra, COMPRA!!!!!", listOf(IntBasedPictureIdentifier(1)), 0))

        // when
        calculateScores.execute(CalculateScoresParams())

        // then
        assertThat(adRepository.findAll()).hasSize(1)
        assertThat(adRepository.findAll()).index(0).transform(transform = Ad::score).transform(transform = Score::points).isEqualTo(15)
    }

    @Test
    fun `given an existing ad with a sixty words description of a Chalet when the score is calculated then 20 is added to the score`() {
        // given
        adRepository.save(Ad(StringBasedAdIdentifier("1"), Typology.CHALET, "This is a description with ten words eight nine ten ".repeat(6), listOf(IntBasedPictureIdentifier(1)), 0))

        // when
        calculateScores.execute(CalculateScoresParams())

        // then
        assertThat(adRepository.findAll()).hasSize(1)
        assertThat(adRepository.findAll()).index(0).transform(transform = Ad::score).transform(transform = Score::points).isEqualTo(35)
    }

    @Test
    fun `given an existing ad with a thirty words description of a Flat when the score is calculated then 10 is added to the score`() {
        // given
        adRepository.save(Ad(StringBasedAdIdentifier("1"), Typology.FLAT, "This is a description with ten words eight nine ten ".repeat(3), listOf(IntBasedPictureIdentifier(1)), 0))

        // when
        calculateScores.execute(CalculateScoresParams())

        // then
        assertThat(adRepository.findAll()).hasSize(1)
        assertThat(adRepository.findAll()).index(0).transform(transform = Ad::score).transform(transform = Score::points).isEqualTo(25)
    }

    @Test
    fun `given an existing ad with a sixty words description of a Flat when the score is calculated then 30 is added to the score`() {
        // given
        adRepository.save(Ad(StringBasedAdIdentifier("1"), Typology.FLAT, "This is a description with ten words eight nine ten ".repeat(6), listOf(IntBasedPictureIdentifier(1)), 0))

        // when
        calculateScores.execute(CalculateScoresParams())

        // then
        assertThat(adRepository.findAll()).hasSize(1)
        assertThat(adRepository.findAll()).index(0).transform(transform = Ad::score).transform(transform = Score::points).isEqualTo(45)
    }

    @Test
    fun `given an existing ad with five buzzwords in the description when the score is calculated then 25 is added to the score`() {
        // given
        adRepository.save(Ad(StringBasedAdIdentifier("1"), Typology.FLAT, "Ático céntrico muy luminoso y recién reformado, parece nuevo", listOf(IntBasedPictureIdentifier(1)), 0))

        // when
        calculateScores.execute(CalculateScoresParams())

        // then
        assertThat(adRepository.findAll()).hasSize(1)
        assertThat(adRepository.findAll()).index(0).transform(transform = Ad::score).transform(transform = Score::points).isEqualTo(40)
    }

    @Test
    fun `given an existing ad which have all the information when the score is calculated then 40 is added to the score`() {
        // given
        adRepository.save(Ad(StringBasedAdIdentifier("1"), Typology.CHALET, "Ático céntrico muy luminoso y recién reformado, parece nuevo", listOf(IntBasedPictureIdentifier(1)), 300, 120))

        // when
        calculateScores.execute(CalculateScoresParams())

        // then
        assertThat(adRepository.findAll()).hasSize(1)
        assertThat(adRepository.findAll()).index(0).transform(transform = Ad::score).transform(transform = Score::points).isEqualTo(80)
    }

    @Test
    fun `given an existing ad of a flat which have all the information when the score is calculated then 40 is added to the score`() {
        // given
        adRepository.save(Ad(StringBasedAdIdentifier("1"), Typology.FLAT, "Ático céntrico muy luminoso y recién reformado, parece nuevo", listOf(IntBasedPictureIdentifier(1)), 300))

        // when
        calculateScores.execute(CalculateScoresParams())

        // then
        assertThat(adRepository.findAll()).hasSize(1)
        assertThat(adRepository.findAll()).index(0).transform(transform = Ad::score).transform(transform = Score::points).isEqualTo(80)
    }
}

inline class StringBasedAdIdentifier(val value: String) : AdIdentifier