package com.example.appshopping.presentation.main.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appshopping.R
import com.example.appshopping.databinding.ItemCartBinding
import com.example.appshopping.domain.model.main.ProductModel

class CartAdapter(
    private val onItemCLicked: (id: String) -> Unit,
    private val onCheckBoxChecked: () -> Unit,
) : ListAdapter<ProductModel, CartAdapter.ViewHolder>(DiffCallback) {

    inner class ViewHolder(private val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(productModel: ProductModel) {
            binding.apply {
                Glide.with(itemView).load(productModel.images[0])
                    .placeholder(R.drawable.loading_img).into(ivProduct)
                tvName.text = productModel.name
                tvPrice.text = productModel.price

                cbSelectProduct.setOnCheckedChangeListener { _, _ ->
                    onCheckBoxChecked()
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
            ItemCartBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
        holder.itemView.setOnClickListener {
            onItemCLicked(currentItem.id)
        }
    }
}