package com.kotlin.mysuperheroeskotlin.ui.presenter

import co.metalab.asyncawait.async
import com.kotlin.mysuperheroeskotlin.common.weak
import com.kotlin.mysuperheroeskotlin.domain.model.DomainError
import com.kotlin.mysuperheroeskotlin.domain.model.SuperHero
import com.kotlin.mysuperheroeskotlin.domain.usecase.GetSuperHeroByName
import com.kotlin.mysuperheroeskotlin.ui.LifecycleSubscriber
import org.funktionale.either.Either

class SuperHeroDetailPresenter(
        view: View,
        private val getSuperHeroByName: GetSuperHeroByName) : LifecycleSubscriber {

    private val view: View? by weak(view)

    private lateinit var name: String

    fun preparePresenter(name: String?) {
        if (name != null) {
            this.name = name
        } else {
            view?.close()
        }
    }

    override fun update() {
        view?.showLoading()
        refreshSuperHero()
    }

    private fun refreshSuperHero() = async {
        val result = await { getSuperHeroByName(name) }
        view?.hideLoading()
        when (result) {
            is Either.Right -> view?.showSuperHero(result.r)
            is Either.Left -> view?.showError(result.l)
        }
    }

    interface View {
        fun close()
        fun showLoading()
        fun hideLoading()
        fun showSuperHero(superHero: SuperHero)
        fun showError(error: DomainError)
    }
}
