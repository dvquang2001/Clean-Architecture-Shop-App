package com.example.appshopping.domain.di

import com.example.appshopping.domain.usecase.check_login.CheckLoginParamUseCaseImpl
import com.example.appshopping.domain.usecase.check_login.CheckLoginUseCase
import com.example.appshopping.domain.usecase.getUserData.GetUserDataUseCase
import com.example.appshopping.domain.usecase.getUserData.GetUserDataUseCaseImpl
import com.example.appshopping.domain.usecase.login.LoginUseCase
import com.example.appshopping.domain.usecase.login.LoginUseCaseImpl
import com.example.appshopping.domain.usecase.register.RegisterUseCase
import com.example.appshopping.domain.usecase.register.RegisterUseCaseImpl
import com.example.appshopping.domain.usecase.reset_password.ResetPasswordUseCase
import com.example.appshopping.domain.usecase.reset_password.ResetPasswordUseCaseImpl
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

    @Binds
    @ViewModelScoped
    abstract fun bindCheckLoginUseCase(impl: CheckLoginParamUseCaseImpl): CheckLoginUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindGetUserDataUseCase(impl: GetUserDataUseCaseImpl): GetUserDataUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindResetPassword(impl: ResetPasswordUseCaseImpl): ResetPasswordUseCase
}