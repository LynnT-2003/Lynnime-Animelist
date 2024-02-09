package com.example.lynnime.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lynnime.R

class ViewPagerAdapter(private val context: Context) : RecyclerView.Adapter<ViewPagerAdapter.PagerViewHolder>() {

    private val images = arrayOf(
        R.drawable.search,
        R.drawable.organize,
        R.drawable.sync
    )

    private val headings = arrayOf(
        R.string.function_1,
        R.string.function_2,
        R.string.function_3
    )

    private val descriptions = arrayOf(
        R.string.desc_1,
        R.string.desc_2,
        R.string.desc_3
    )

    inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.titleImage)
        val heading: TextView = itemView.findViewById(R.id.textTitle)
        val description: TextView = itemView.findViewById(R.id.textDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.slider_layout, parent, false)
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.image.setImageResource(images[position])
        holder.heading.setText(headings[position])
        holder.description.setText(descriptions[position])
    }

    override fun getItemCount(): Int = headings.size
}
