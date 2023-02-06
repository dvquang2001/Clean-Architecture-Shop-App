package com.example.appshopping.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.appshopping.R
import com.example.appshopping.databinding.ActivityMainBinding
import com.example.appshopping.presentation.main.home_screen.HomeFragment
import com.example.appshopping.presentation.main.setting_screen.SettingFragment
import com.example.appshopping.presentation.main.user_screen.UserInfoFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val homeFragment = HomeFragment()
        val userInfoFragment = UserInfoFragment()
        val settingFragment = SettingFragment()

        setCurrentFragment(homeFragment)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.menuHome -> setCurrentFragment(homeFragment)
                R.id.menuUserInfo -> setCurrentFragment(userInfoFragment)
                R.id.menuSetting -> setCurrentFragment(settingFragment)
            }
            true
        }

    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
    }


}