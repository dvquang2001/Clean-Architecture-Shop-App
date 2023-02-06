package com.example.appshopping.data.di

import com.example.appshopping.data.repository.AuthRepositoryImpl
import com.example.appshopping.data.repository.MainRepositoryImpl
import com.example.appshopping.domain.repository.AuthRepository
import com.example.appshopping.domain.repository.MainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataAppModule {

    @Binds
    @ViewModelScoped
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @ViewModelScoped
    abstract fun bindMainRepository(mainRepositoryImpl: MainRepositoryImpl): MainRepository

}