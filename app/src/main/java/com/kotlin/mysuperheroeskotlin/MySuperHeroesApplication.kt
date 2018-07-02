package com.kotlin.mysuperheroeskotlin

import android.app.Application
import android.content.Context
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.conf.ConfigurableKodein
import com.karumi.marvelapiclient.CharacterApiClient
import com.karumi.marvelapiclient.MarvelApiConfig
import com.kotlin.mysuperheroeskotlin.common.RealTimeProvider
import com.kotlin.mysuperheroeskotlin.common.TimeProvider
import com.kotlin.mysuperheroeskotlin.data.datasource.MemorySuperHeroDataSource
import com.kotlin.mysuperheroeskotlin.data.datasource.NetworkSuperHeroDataSource
import com.kotlin.mysuperheroeskotlin.data.datasource.SuperHeroDataSource
import com.kotlin.mysuperheroeskotlin.data.repository.SuperHeroRepository

class MySuperHeroesApplication : Application(), KodeinAware {

    override val kodein = ConfigurableKodein(mutable = true)
    var overrideModule: Kodein.Module? = null

    override fun onCreate() {
        super.onCreate()
        resetInjection()
    }

    fun addModule(activityModules: Kodein.Module) {
        kodein.addImport(activityModules, true)
        if (overrideModule != null) {
            kodein.addImport(overrideModule!!, true)
        }
    }

    fun resetInjection() {
        kodein.clear()
        kodein.addImport(appDependencies(), true)
    }

    private fun appDependencies(): Kodein.Module {
        return Kodein.Module(allowSilentOverride = true) {
            bind<SuperHeroRepository>() with provider {
                SuperHeroRepository(listOf(instance<SuperHeroDataSource>(),
                        NetworkSuperHeroDataSource(instance())))
            }
            bind<SuperHeroDataSource>() with singleton {
                MemorySuperHeroDataSource(instance())
            }
            bind<TimeProvider>() with instance(RealTimeProvider())
            bind<CharacterApiClient>() with provider { CharacterApiClient(instance()) }
            bind<MarvelApiConfig>() with instance(
                    MarvelApiConfig.with(BuildConfig.MARVEL_PUBLIC_KEY, BuildConfig.MARVEL_PRIVATE_KEY))
        }
    }
}

fun Context.asApp() = this.applicationContext as MySuperHeroesApplication