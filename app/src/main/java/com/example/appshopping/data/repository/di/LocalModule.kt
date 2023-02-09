package com.example.appshopping.data.repository.di

import android.app.Application
import androidx.navigation.Navigator
import com.example.appshopping.other.Constant
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideSharedPreference(application: Application) = application.getSharedPreferences(
        Constant.SHARED_PREFS, 0
    )

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    @Named("product")
    fun provideFireStoreProductCollection(): CollectionReference =
        Firebase.firestore.collection("products")

    @Provides
    @Singleton
    @Named("user")
    fun provideFireStoreUserCollection(): CollectionReference =
        Firebase.firestore.collection("users")

}