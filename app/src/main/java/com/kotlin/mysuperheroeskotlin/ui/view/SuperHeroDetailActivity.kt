package com.kotlin.mysuperheroeskotlin.ui.view

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.Toolbar
import android.view.View
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.kotlin.mysuperheroeskotlin.R
import com.kotlin.mysuperheroeskotlin.domain.model.SuperHero
import com.kotlin.mysuperheroeskotlin.domain.usecase.GetSuperHeroByName
import com.kotlin.mysuperheroeskotlin.ui.presenter.SuperHeroDetailPresenter
import com.kotlin.mysuperheroeskotlin.ui.utils.setImageBackground
import kotlinx.android.synthetic.main.super_hero_detail_activity.*

class SuperHeroDetailActivity : BaseActivity(), SuperHeroDetailPresenter.View {

    companion object {
        private val SUPER_HERO_NAME_KEY = "super_hero_name_key"

        fun open(activity: Activity, superHeroName: String) {
            val intent = Intent(activity, SuperHeroDetailActivity::class.java)
            intent.putExtra(SUPER_HERO_NAME_KEY, superHeroName)
            activity.startActivity(intent)
        }
    }

    override val presenter: SuperHeroDetailPresenter by injector.instance()
    override val layoutId: Int = R.layout.super_hero_detail_activity
    override val toolbarView: Toolbar
        get() = toolbar

    override fun preparePresenter(intent: Intent?) {
        val superId = intent?.extras?.getString(SUPER_HERO_NAME_KEY)
        title = ""
        presenter.preparePresenter(superId)
    }

    override fun close() = finish()

    override fun showLoading() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress_bar.visibility = View.GONE
    }

    override fun showSuperHero(superHero: SuperHero) {
        title = superHero.name
        tv_super_hero_name.text = superHero.name
        tv_super_hero_description.text = superHero.description
        iv_avengers_badge.visibility =
                if (superHero.isAvenger) View.VISIBLE else View.GONE
        iv_super_hero_photo.setImageBackground(superHero.photo)
    }

    override val activityModules = Kodein.Module(allowSilentOverride = true) {
        bind<SuperHeroDetailPresenter>() with provider {
            SuperHeroDetailPresenter(this@SuperHeroDetailActivity, instance())
        }
        bind<GetSuperHeroByName>() with provider { GetSuperHeroByName(instance()) }
    }
}