package com.example.kotlinplacestest.viewholder

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinplacestest.R
import com.example.kotlinplacestest.gson.places.Results
import kotlinx.android.synthetic.main.places_item.view.*

class PlacesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindView(place: Results, index: Int) {
        itemView.address_tv?.text = place.formatted_address

        itemView.setOnClickListener {
            val shake: Animation = AnimationUtils.loadAnimation(itemView.context, R.anim.shake)
            itemView.startAnimation(shake)
        }
    }
}
