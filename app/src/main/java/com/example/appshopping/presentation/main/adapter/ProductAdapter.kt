package com.example.appshopping.presentation.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appshopping.R
import com.example.appshopping.databinding.ItemProductBinding
import com.example.appshopping.domain.model.main.ProductModel

//class ProductAdapter(
//    private val onItemClicked: (ProductModel) -> Unit
//) : ListAdapter<ProductModel,ProductAdapter.ViewHolder>(Diffcallback){
//
//    class ViewHolder(private val binding: ItemProductBinding): RecyclerView.ViewHolder(binding.root) {
//        fun bind(productModel: ProductModel) {
//            binding.apply {
//                ivProduct.setImageResource(productModel.image)
//                tvProductName.text = productModel.name
//                tvProductPrice.text = productModel.price
//            }
//        }
//    }
//
//    companion object {
//        private val Diffcallback = object : DiffUtil.ItemCallback<ProductModel>() {
//            override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
//                return oldItem.name == newItem.name
//            }
//
//            override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
//                return oldItem == newItem
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ViewHolder(
//            ItemProductBinding.inflate(LayoutInflater.from(parent.context))
//        )
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//       val currentProduct = getItem(position)
//        holder.bind(currentProduct)
//        holder.itemView.setOnClickListener {
//            onItemClicked(currentProduct)
//        }
//    }
//}

class ProductAdapter(
    private val images: List<ProductModel>,
    private val onItemClicked: (ProductModel) -> Unit,
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(productModel: ProductModel) {
            val image = itemView.findViewById<ImageView>(R.id.ivProduct)
            val name = itemView.findViewById<TextView>(R.id.tvProductName)
            val price = itemView.findViewById<TextView>(R.id.tvProductPrice)

            image.setImageResource(productModel.image)
            name.text = productModel.name
            price.text = productModel.price
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentProduct = images[position]
        holder.bind(currentProduct)
        holder.itemView.setOnClickListener {
            onItemClicked(currentProduct)
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }
}