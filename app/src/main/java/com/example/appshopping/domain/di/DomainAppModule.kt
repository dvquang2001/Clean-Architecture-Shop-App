package com.example.appshopping.domain.di

import com.example.appshopping.domain.usecase.login.LoginUseCase
import com.example.appshopping.domain.usecase.login.LoginUseCaseImpl
import com.example.appshopping.domain.usecase.register.RegisterUseCase
import com.example.appshopping.domain.usecase.register.RegisterUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class DomainAppModule {

    @Binds
    @ViewModelScoped
    abstract fun bindLoginUseCase(impl: LoginUseCaseImpl): LoginUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindRegisterUseCase(impl: RegisterUseCaseImpl): RegisterUseCase
}