package com.my_project.airanimation.presentation.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.my_project.airanimation.R
import com.my_project.airanimation.data.entities.City

class SearchAdapter : ListAdapter<City, SearchAdapter.TasksHolder>(CityDiffCallback()) {

    private var action: (item: City) -> Unit = { }

    fun onItemClick(action: (item: City) -> Unit){
        this.action = action
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return TasksHolder(view)
    }

    override fun onBindViewHolder(holder: TasksHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    inner class TasksHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {
        private var townTextView = containerView.findViewById(R.id.townTextView) as AppCompatTextView
        private var fullNameTextView = containerView.findViewById(R.id.fullNameTextView) as AppCompatTextView
        private lateinit var city: City
        init { containerView.setOnClickListener { action(city) } }
        fun bindTo(city: City) {
            this.city = city
            with(city) {
                townTextView.text = town
                fullNameTextView.text = fullName
            }
        }
    }
}