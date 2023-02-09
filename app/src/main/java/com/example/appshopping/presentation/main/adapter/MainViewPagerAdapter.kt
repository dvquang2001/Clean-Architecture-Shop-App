package com.example.appshopping.presentation.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.appshopping.presentation.main.home_screen.HomeFragment
import com.example.appshopping.presentation.main.setting_screen.SettingFragment
import com.example.appshopping.presentation.main.user_screen.UserInfoFragment

class MainViewPagerAdapter(private val fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> UserInfoFragment()
            2 -> SettingFragment()
            else -> HomeFragment()
        }
    }
}