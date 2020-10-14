package com.idealista.infrastructure.persistence

import com.idealista.domain.*
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class InMemoryPersistence : AdRepository, PictureRepository {
    final val ads: MutableList<AdVO>
    private final val pictures: MutableList<PictureVO>

    init {
        ads = ArrayList()
        ads.add(AdVO(1, "CHALET", "Este piso es una ganga, compra, compra, COMPRA!!!!!", emptyList(), 300, null, null, null))
        ads.add(AdVO(2, "FLAT", "Nuevo ático céntrico recién reformado. No deje pasar la oportunidad y adquiera este ático de lujo", listOf(4), 300, null, null, null))
        ads.add(AdVO(3, "CHALET", "", listOf(2), 300, null, null, null))
        ads.add(AdVO(4, "FLAT", "Ático céntrico muy luminoso y recién reformado, parece nuevo", listOf(5), 300, null, null, null))
        ads.add(AdVO(5, "FLAT", "Pisazo,", listOf(3, 8), 300, null, null, null))
        ads.add(AdVO(6, "GARAGE", "", listOf(6), 300, null, null, null))
        ads.add(AdVO(7, "GARAGE", "Garaje en el centro de Albacete", emptyList(), 300, null, null, null))
        ads.add(AdVO(8, "CHALET", "Maravilloso chalet situado en lAs afueras de un pequeño pueblo rural. El entorno es espectacular, las vistas magníficas. ¡Cómprelo ahora!", listOf(1, 7), 300, null, null, null))
        pictures = ArrayList<PictureVO>()
        pictures.add(PictureVO(1, "http://www.idealista.com/pictures/1", "SD"))
        pictures.add(PictureVO(2, "http://www.idealista.com/pictures/2", "HD"))
        pictures.add(PictureVO(3, "http://www.idealista.com/pictures/3", "SD"))
        pictures.add(PictureVO(4, "http://www.idealista.com/pictures/4", "HD"))
        pictures.add(PictureVO(5, "http://www.idealista.com/pictures/5", "SD"))
        pictures.add(PictureVO(6, "http://www.idealista.com/pictures/6", "SD"))
        pictures.add(PictureVO(7, "http://www.idealista.com/pictures/7", "SD"))
        pictures.add(PictureVO(8, "http://www.idealista.com/pictures/8", "HD"))
    }

    override fun findAll(): List<Ad> {
        return ads.map {
            mapToDomain(it)
        }.toList()
    }

    override fun saveAll(ads: List<Ad>): List<Ad> {
        ads.map {
            mapToVO(it)
        }.forEach {
            save(it)
        }
        return ads
    }

    override fun findByIdentifier(identifier: PictureIdentifier): Picture? {
        return pictures.find {
            it.id == identifier.toString().toInt()
        }.let {
            Picture(IntBasedPictureIdentifier(it?.id ?: 0), it?.url ?: "", Quality.fromAcronym(it?.quality ?: ""))
        }
    }

    private fun save(it: AdVO) {
        this.ads[(it.id) - 1] = it
    }

    private fun mapToDomain(adVO: AdVO) =
            Ad(StringBasedAdIdentifier(adVO.id.toString()), Typology.valueOf(adVO.typology), Description(adVO.description), getPictures(adVO).toList(), adVO.houseSize, adVO.gardenSize, adVO.irrelevantSince, adVO.score?.let { Score(it) }
                    ?: Score.empty())

    private fun getPictures(adVO: AdVO): List<Picture> {
        return adVO.pictures.map {
            mapToDomain(it, pictures[it - 1])
        }
    }

    private fun mapToDomain(id: Int, pictureVO: PictureVO) =
            Picture(IntBasedPictureIdentifier(id), pictureVO.url, Quality.fromAcronym(pictureVO.quality ?: ""))

    private fun mapToVO(ad: Ad) =
            AdVO(ad.id.toString().toInt(), ad.typology.name, ad.description.content, ad.pictures.map { pictureIdentifier -> pictureIdentifier.identifier.toString().toInt() }, ad.houseSize, ad.gardenSize, ad.score.points, ad.irrelevantSince)
}

inline class StringBasedAdIdentifier(private val value: String) : AdIdentifier {
    override fun toString(): String {
        return value
    }
}

inline class IntBasedPictureIdentifier(private val value: Int) : PictureIdentifier {
    override fun toString(): String {
        return "$value"
    }
}