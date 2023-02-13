package com.example.appshopping.presentation.main.detail_screen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appshopping.R

class ProductImageViewPagerAdapter(
    private val images: List<String>,
) : RecyclerView.Adapter<ProductImageViewPagerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(imageUrl: String) {
            val currentImage = itemView.findViewById<ImageView>(R.id.ivImage)
            Glide.with(itemView).load(imageUrl)
                .placeholder(R.drawable.loading_img)
                .into(currentImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view_pager,parent,false)
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