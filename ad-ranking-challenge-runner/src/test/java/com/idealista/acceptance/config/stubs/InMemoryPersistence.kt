package com.idealista.acceptance.config.stubs

import com.idealista.infrastructure.persistence.AdVO
import com.idealista.infrastructure.persistence.PictureVO
import java.util.*

class InMemoryPersistence {
    val ads: MutableList<AdVO>
    val pictures: MutableList<PictureVO>

    init {
        ads = ArrayList()
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
}