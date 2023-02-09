package com.example.appshopping.presentation.main.product.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.appshopping.presentation.main.product.FragmentAllProduct
import com.example.appshopping.presentation.main.product.FragmentDeviceProduct
import com.example.appshopping.presentation.main.product.FragmentFashionProduct

class ProductViewPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> FragmentAllProduct()
            1 -> FragmentDeviceProduct()
            2 -> FragmentFashionProduct()
            else -> FragmentAllProduct()
        }
    }
}