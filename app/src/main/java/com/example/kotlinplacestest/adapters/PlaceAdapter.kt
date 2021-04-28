package com.example.kotlinplacestest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinplacestest.R
import com.example.kotlinplacestest.gson.places.Results
import com.example.kotlinplacestest.viewholder.PlacesViewHolder

class PlaceAdapter : RecyclerView.Adapter<PlacesViewHolder>() {

    private var list = listOf<Results?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesViewHolder {
        return PlacesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.places_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PlacesViewHolder, position: Int) {
        list[position]?.let { holder.bindView(it, position) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(list: List<Results?>) {
        this.list = list
    }

}