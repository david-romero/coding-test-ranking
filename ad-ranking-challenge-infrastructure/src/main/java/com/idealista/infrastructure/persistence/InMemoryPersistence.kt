package com.idealista.infrastructure.persistence

import org.springframework.stereotype.Repository
import java.util.*

@Repository
class InMemoryPersistence {
    final val ads: MutableList<AdVO>
    final val pictures: MutableList<PictureVO>

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
    } //TODO crea los métodos que necesites
}