package com.kotlin.mysuperheroeskotlin.data.repository

import com.kotlin.mysuperheroeskotlin.common.optionalRight
import com.kotlin.mysuperheroeskotlin.data.datasource.SuperHeroDataSource
import com.kotlin.mysuperheroeskotlin.domain.model.DomainError
import com.kotlin.mysuperheroeskotlin.domain.model.SuperHero
import org.funktionale.either.Either

class SuperHeroRepository(private val datasources: List<SuperHeroDataSource>) {

    fun getAllSuperHeroes(): Either<DomainError, List<SuperHero>> {
        val values = datasources.first { it.isUpdated() }.getAll()
        values.optionalRight()?.let {
            list -> datasources.forEach {
                it.populate(list)
            }
        }

        return values
    }

    fun getByName(key: String): Either<DomainError, SuperHero> =
            datasources.first { it.isUpdated() && it.contains(key) }.get(key)
}