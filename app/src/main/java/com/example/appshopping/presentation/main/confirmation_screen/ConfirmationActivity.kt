package com.example.appshopping.presentation.main.confirmation_screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.appshopping.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmationActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confimation_screen)

        // Retrieve NavController from the NavHostFragment
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_confirmation) as NavHostFragment
        navController = navHostFragment.navController
    }
}