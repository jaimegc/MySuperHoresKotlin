package com.kotlin.mysuperheroeskotlin.domain.usecase

import com.kotlin.mysuperheroeskotlin.data.repository.SuperHeroRepository
import com.kotlin.mysuperheroeskotlin.domain.model.DomainError
import com.kotlin.mysuperheroeskotlin.domain.model.SuperHero
import org.funktionale.either.Either

class GetSuperHeroByName(private val superHeroRepository: SuperHeroRepository) {

    operator fun invoke(name: String): Either<DomainError, SuperHero> = superHeroRepository.getByName(name)
}