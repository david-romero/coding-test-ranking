package com.idealista.usecases.stubs

import com.idealista.domain.Ad
import com.idealista.domain.AdRepository

class AdRepositoryStub : AdRepository {

    private val database: MutableMap<String, Ad> = HashMap()

    override fun findAll(): List<Ad> = database.values.toList()

    override fun saveAll(ads: List<Ad>): List<Ad> {
        ads.forEach {
            save(it)
        }
        return ads
    }

    fun save(ad: Ad) = database.put(ad.id.toString(), ad)
}