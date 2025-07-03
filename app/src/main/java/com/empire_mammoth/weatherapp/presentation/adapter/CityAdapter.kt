package com.empire_mammoth.weatherapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.empire_mammoth.weatherapp.databinding.ItemCityBinding
import com.empire_mammoth.weatherapp.domain.model.City

class CityAdapter(
    private val onClick: (City) -> Unit
) : ListAdapter<City, CityAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(binding: ItemCityBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(city: City) {
            ItemCityBinding.bind(itemView).apply {
                textCityName.text = city.name
                textCountry.text = city.country
                iconSelected.visibility = if (city.isCurrent) View.VISIBLE else View.GONE
                root.setOnClickListener { onClick(city) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: City, newItem: City) = oldItem == newItem
    }
}