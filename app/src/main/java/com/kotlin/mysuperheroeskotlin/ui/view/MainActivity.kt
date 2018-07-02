package com.kotlin.mysuperheroeskotlin.ui.view

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.kotlin.mysuperheroeskotlin.R
import com.kotlin.mysuperheroeskotlin.domain.model.SuperHero
import com.kotlin.mysuperheroeskotlin.domain.usecase.GetSuperHeroes
import com.kotlin.mysuperheroeskotlin.ui.presenter.SuperHeroesPresenter
import com.kotlin.mysuperheroeskotlin.ui.view.adapter.SuperHeroesAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), SuperHeroesPresenter.View {

    override val presenter: SuperHeroesPresenter by injector.instance()
    private lateinit var adapter: SuperHeroesAdapter
    override val layoutId = R.layout.activity_main
    override val toolbarView: Toolbar
        get() = toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeAdapter()
        initializeRecyclerView()
    }

    private fun initializeAdapter() {
        adapter = SuperHeroesAdapter(presenter)
    }

    private fun initializeRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        recycler_view.adapter = adapter
    }

    override fun hideLoading() {
        progress_bar.visibility = View.GONE
    }

    override fun showSuperHeroes(superHeroes: List<SuperHero>) {
        adapter.clear()
        adapter.addAll(superHeroes)
        adapter.notifyDataSetChanged()
    }

    override fun showLoading() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun showEmptyCase() {
        tv_empty_case.visibility = View.VISIBLE
    }

    override fun openDetail(name: String) {
        SuperHeroDetailActivity.open(activity = this, superHeroName = name)
    }

    override val activityModules = Kodein.Module(allowSilentOverride = true) {
        bind<SuperHeroesPresenter>() with provider {
            SuperHeroesPresenter(this@MainActivity, instance())
        }
        bind<GetSuperHeroes>() with provider { GetSuperHeroes(instance()) }
    }
}
