package com.kotlin.mysuperheroeskotlin.ui.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.kotlin.mysuperheroeskotlin.R
import com.kotlin.mysuperheroeskotlin.domain.model.SuperHero
import com.kotlin.mysuperheroeskotlin.ui.presenter.SuperHeroesPresenter
import com.kotlin.mysuperheroeskotlin.ui.utils.setImageBackground
import org.jetbrains.anko.find

class SuperHeroViewHolder(
        itemView: View,
        private val presenter: SuperHeroesPresenter) : RecyclerView.ViewHolder(itemView) {

    // Two ways to use findViewById
    private val photoImageView: ImageView = itemView.findViewById(R.id.iv_super_hero_photo)
    private val nameTextView = itemView.find<TextView>(R.id.tv_super_hero_name)
    private val avengersBadgeView = itemView.find<View>(R.id.iv_avengers_badge)

    fun render(superHero: SuperHero) {
        hookListeners(superHero)
        renderSuperHeroPhoto(superHero.photo)
        renderSuperHeroName(superHero.name)
        renderAvengersBadge(superHero.isAvenger)
    }

    private fun hookListeners(superHero: SuperHero) {
        itemView.setOnClickListener { presenter.onSuperHeroClicked(superHero) }
    }

    private fun renderSuperHeroPhoto(photo: String?) {
        // Picasso Utils extension
        photoImageView.setImageBackground(photo)
    }

    private fun renderSuperHeroName(name: String) {
        nameTextView.text = name
    }

    private fun renderAvengersBadge(isAvenger: Boolean) {
        avengersBadgeView.visibility = if (isAvenger) View.VISIBLE else View.GONE
    }
}
