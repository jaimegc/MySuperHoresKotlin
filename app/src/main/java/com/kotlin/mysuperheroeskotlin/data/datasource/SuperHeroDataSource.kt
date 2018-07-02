package com.kotlin.mysuperheroeskotlin.data.datasource

import com.kotlin.mysuperheroeskotlin.domain.model.DomainError
import com.kotlin.mysuperheroeskotlin.domain.model.SuperHero
import org.funktionale.either.Either

interface SuperHeroDataSource {

    fun get(key: String): Either<DomainError, SuperHero>
    fun getAll(): Either<DomainError, List<SuperHero>>
    fun isUpdated(): Boolean = true
    fun populate(superHeroes: List<SuperHero>) {}
    fun contains(key: String): Boolean = true
}