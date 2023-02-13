package com.example.appshopping.presentation.main.confirmation_screen.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appshopping.R
import com.example.appshopping.databinding.ItemPurchasedCartBinding
import com.example.appshopping.domain.model.main.ProductModel

class PurchasedCartAdapter(
    private val context: Context,
    private val cartDesc: String,
    private val onItemCLicked: (id: String) -> Unit
) : ListAdapter<ProductModel, PurchasedCartAdapter.ViewHolder>(DiffCallback) {

    inner class ViewHolder(private val binding: ItemPurchasedCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(productModel: ProductModel) {
            binding.apply {
                Glide.with(itemView).load(productModel.images[0])
                    .placeholder(R.drawable.loading_img).into(ivProduct)
                tvName.text = productModel.name
                tvPrice.text = context.getString(R.string.price,productModel.price)
                tvItemDesc.text = cartDesc

                layoutItem.setOnClickListener {
                    onItemCLicked(productModel.id)
                }
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<ProductModel>() {
            override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPurchasedCartBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }
}