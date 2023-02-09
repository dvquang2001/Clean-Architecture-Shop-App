package com.example.appshopping.domain.di

import com.example.appshopping.domain.usecase.auth.logout.LogoutUseCase
import com.example.appshopping.domain.usecase.auth.logout.LogoutUseCaseImpl
import com.example.appshopping.domain.usecase.auth.check_login.CheckLoginParamUseCaseImpl
import com.example.appshopping.domain.usecase.auth.check_login.CheckLoginUseCase
import com.example.appshopping.domain.usecase.auth.get_user_data.GetUserDataUseCase
import com.example.appshopping.domain.usecase.auth.get_user_data.GetUserDataUseCaseImpl
import com.example.appshopping.domain.usecase.auth.login.LoginUseCase
import com.example.appshopping.domain.usecase.auth.login.LoginUseCaseImpl
import com.example.appshopping.domain.usecase.auth.register.RegisterUseCase
import com.example.appshopping.domain.usecase.auth.register.RegisterUseCaseImpl
import com.example.appshopping.domain.usecase.auth.reset_password.ResetPasswordUseCase
import com.example.appshopping.domain.usecase.auth.reset_password.ResetPasswordUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class DomainAuthAppModule {

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

    @Binds
    @ViewModelScoped
    abstract fun bindLogoutUseCase(impl: LogoutUseCaseImpl): LogoutUseCase


}