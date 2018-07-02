package com.kotlin.mysuperheroeskotlin.ui.view

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.Toolbar
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.KodeinAppCompatActivity
import com.kotlin.mysuperheroeskotlin.ui.LifecyclePublisher
import com.kotlin.mysuperheroeskotlin.ui.LifecycleSubscriber
import com.kotlin.mysuperheroeskotlin.ui.lifeCycleLinker
import com.kotlin.mysuperheroeskotlin.asApp
import com.kotlin.mysuperheroeskotlin.domain.model.DomainError

abstract class BaseActivity : KodeinAppCompatActivity(), LifecyclePublisher by lifeCycleLinker {

    abstract val layoutId: Int
    abstract val presenter: LifecycleSubscriber
    abstract val toolbarView: Toolbar
    abstract val activityModules: Kodein.Module

    override fun onCreate(savedInstanceState: Bundle?) {
        applicationContext.asApp().addModule(activityModules)
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        setSupportActionBar(toolbarView)
        register(presenter)
        preparePresenter(intent)
        initialize()
    }

    open fun preparePresenter(intent: Intent?) {}

    override fun onResume() {
        super.onResume()
        update()
    }

    override fun onDestroy() {
        unregister(presenter)
        super.onDestroy()
    }

    fun showError(error: DomainError) =
            Snackbar.make(toolbarView, error.asString(this), Snackbar.LENGTH_LONG).show()

}