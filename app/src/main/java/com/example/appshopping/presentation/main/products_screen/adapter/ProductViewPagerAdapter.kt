package com.example.appshopping.presentation.main.products_screen.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.appshopping.presentation.main.products_screen.FragmentAllProduct
import com.example.appshopping.presentation.main.products_screen.FragmentDeviceProduct
import com.example.appshopping.presentation.main.products_screen.FragmentFashionProduct

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