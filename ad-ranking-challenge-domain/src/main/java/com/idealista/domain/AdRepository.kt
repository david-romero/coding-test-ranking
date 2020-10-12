package com.idealista.domain

interface AdRepository {

    fun findAll(): List<Ad>

    fun saveAll(ads: List<Ad>): List<Ad>

}