package com.example.appshopping.presentation.main.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.example.appshopping.R
import com.example.appshopping.base.BaseActivity
import com.example.appshopping.databinding.ActivityMainBinding
import com.example.appshopping.presentation.main.home.adapter.MainViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.saveUserToStorage()

        val adapter = MainViewPagerAdapter(this)
        binding.viewPagerMain.adapter = adapter

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menuHome -> binding.viewPagerMain.currentItem = 0
                R.id.menuUserInfo -> binding.viewPagerMain.currentItem = 1
                R.id.menuSetting -> binding.viewPagerMain.currentItem = 2
            }
            true
        }

        binding.viewPagerMain.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when(position) {
                    0 -> binding.bottomNavigationView.menu.findItem(R.id.menuHome).isChecked = true
                    1 -> binding.bottomNavigationView.menu.findItem(R.id.menuUserInfo).isChecked = true
                    2 -> binding.bottomNavigationView.menu.findItem(R.id.menuSetting).isChecked = true
                }
            }
        })

        initObserver()
    }

    private fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.saveUserToStorage()
            }
        }
    }


}