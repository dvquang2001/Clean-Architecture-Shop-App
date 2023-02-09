package com.example.appshopping.presentation.main.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appshopping.R
import com.example.appshopping.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

    }
}