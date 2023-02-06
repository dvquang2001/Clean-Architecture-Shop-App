package com.example.appshopping.presentation.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.example.appshopping.R

class ViewPagerAdapter(
    private val images: List<Int>,
) : RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(@DrawableRes imgResource: Int) {
            val currentImage = itemView.findViewById<ImageView>(R.id.ivImage)
            currentImage.setImageResource(imgResource)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_pager, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = images[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return images.size
    }
}