package com.kotlin.mysuperheroeskotlin.data.datasource

import com.kotlin.mysuperheroeskotlin.common.TimeProvider
import com.kotlin.mysuperheroeskotlin.domain.model.DomainError
import com.kotlin.mysuperheroeskotlin.domain.model.NotIndexFoundDomainError
import com.kotlin.mysuperheroeskotlin.domain.model.SuperHero
import org.funktionale.either.Either
import java.util.concurrent.TimeUnit

class MemorySuperHeroDataSource(private val timeProvider: TimeProvider) : SuperHeroDataSource {

    companion object {
        private val TIME_UPDATE = TimeUnit.SECONDS.toMillis(30)
    }

    private val cache = LinkedHashMap<String, SuperHero>()
    private var lastUpdate = 0L

    override fun get(key: String): Either<DomainError, SuperHero> =
        cache[key]?.let { Either.right(it) } ?: Either.left(NotIndexFoundDomainError(key))

    override fun isUpdated(): Boolean = timeProvider.time() - lastUpdate < TIME_UPDATE

    override fun contains(key: String): Boolean = cache.contains(key)

    override fun getAll(): Either<DomainError, List<SuperHero>> =
        Either.right(ArrayList(cache.values))

    override fun populate(superHeroes: List<SuperHero>) {
        lastUpdate = timeProvider.time()
        cache.putAll(superHeroes.map { it.id to it})
    }
}