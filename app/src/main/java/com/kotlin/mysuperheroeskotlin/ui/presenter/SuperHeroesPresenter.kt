package com.kotlin.mysuperheroeskotlin.ui.presenter

import co.metalab.asyncawait.async
import com.kotlin.mysuperheroeskotlin.common.weak
import com.kotlin.mysuperheroeskotlin.domain.model.DomainError
import com.kotlin.mysuperheroeskotlin.domain.model.SuperHero
import com.kotlin.mysuperheroeskotlin.domain.usecase.GetSuperHeroes
import com.kotlin.mysuperheroeskotlin.ui.LifecycleSubscriber
import org.funktionale.either.Either

class SuperHeroesPresenter(
        view: View,
        private val getSuperHeroes: GetSuperHeroes) : LifecycleSubscriber {

    private val view: View? by weak(view)

    override fun update() {
        view?.showLoading()
        refreshSuperHeroes()
    }

    private fun refreshSuperHeroes() = async {
        val result = await { getSuperHeroes() }
        view?.hideLoading()

        when (result) {
            is Either.Right -> showSuperHerores(result.r)
            is Either.Left -> view?.showError(result.l)
        }
    }

    private fun showSuperHerores(result: List<SuperHero>) {
        when {
            result.isEmpty() -> view?.showEmptyCase()
            else -> view?.showSuperHeroes(result)
        }
    }

    fun onSuperHeroClicked(superHero: SuperHero) = view?.openDetail(superHero.id)

    interface View {
        fun hideLoading()

        fun showSuperHeroes(superHeroes: List<SuperHero>)

        fun showLoading()

        fun showEmptyCase()

        fun openDetail(name: String)

        fun showError(error: DomainError)
    }
}