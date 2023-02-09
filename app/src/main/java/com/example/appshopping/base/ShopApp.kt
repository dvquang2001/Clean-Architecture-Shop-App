package com.example.appshopping.base

import android.app.Application
import androidx.preference.PreferenceManager
import com.example.appshopping.other.Constant.LANGUAGE_SELECTION
import dagger.hilt.android.HiltAndroidApp
import java.util.*

@HiltAndroidApp
class ShopApp: Application() {

    override fun onCreate() {
        super.onCreate()
        var change = ""
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val language = sharedPreferences.getString(LANGUAGE_SELECTION,"English")
        change = if(language == "English") {
            "en"
        } else {
            "vi"
        }
        BaseActivity.dLocale = Locale(change)
    }
}