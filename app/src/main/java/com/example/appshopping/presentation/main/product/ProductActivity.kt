package com.example.appshopping.presentation.main.product

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appshopping.R
import com.example.appshopping.databinding.ActivityProductBinding
import com.example.appshopping.presentation.main.product.adapter.ProductViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPagerAdapter = ProductViewPagerAdapter(this)
        binding.productViewPager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout,binding.productViewPager) { tabLayout, position ->
            when(position) {
                0 -> tabLayout.setText(R.string.all)
                1 -> tabLayout.setText(R.string.device)
                2 -> tabLayout.setText(R.string.fashion)
            }
        }.attach()

    }


}