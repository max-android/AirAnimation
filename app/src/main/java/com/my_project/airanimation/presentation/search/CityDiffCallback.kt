package com.my_project.airanimation.presentation.search

import androidx.recyclerview.widget.DiffUtil
import com.my_project.airanimation.data.entities.City


class CityDiffCallback : DiffUtil.ItemCallback<City>() {
    override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
        return oldItem == newItem
    }
}