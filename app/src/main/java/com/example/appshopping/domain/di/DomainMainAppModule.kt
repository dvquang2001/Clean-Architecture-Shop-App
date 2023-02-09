package com.example.appshopping.domain.di

import com.example.appshopping.domain.usecase.main.change_password.ChangePasswordUseCase
import com.example.appshopping.domain.usecase.main.change_password.ChangePasswordUseCaseImpl
import com.example.appshopping.domain.usecase.main.change_user_info.ChangeUserInfoUseCase
import com.example.appshopping.domain.usecase.main.change_user_info.ChangeUserInfoUseCaseImpl
import com.example.appshopping.domain.usecase.main.get_products.GetProductsUseCase
import com.example.appshopping.domain.usecase.main.get_products.GetProductsUseCaseImpl
import com.example.appshopping.domain.usecase.main.get_products_by_user_id.GetProductsByUserIdUseCase
import com.example.appshopping.domain.usecase.main.get_products_by_user_id.GetProductsByUserIdUseCaseImpl
import com.example.appshopping.domain.usecase.main.get_single_product.GetProductUseCase
import com.example.appshopping.domain.usecase.main.get_single_product.GetProductUseCaseImpl
import com.example.appshopping.domain.usecase.main.get_user.GetUserInfoUseCase
import com.example.appshopping.domain.usecase.main.get_user.GetUserInfoUseCaseImpl
import com.example.appshopping.domain.usecase.main.save_user.SaveUserUseCase
import com.example.appshopping.domain.usecase.main.save_user.SaveUserUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class DomainMainAppModule {

    @Binds
    @ViewModelScoped
    abstract fun bindGetProductsUseCase(getProductsUseCaseImpl: GetProductsUseCaseImpl): GetProductsUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindGetProductUseCase(getProductUseCaseImpl: GetProductUseCaseImpl): GetProductUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindGetUserInfoUseCase(getUserInfoUseCaseImpl: GetUserInfoUseCaseImpl): GetUserInfoUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindSaveUserUseCase(saveUserUseCaseImpl: SaveUserUseCaseImpl): SaveUserUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindChangeUserInfoUseCase(changeUserInfoUseCaseImpl: ChangeUserInfoUseCaseImpl): ChangeUserInfoUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindChangePasswordUseCase(changePasswordUseCaseImpl: ChangePasswordUseCaseImpl): ChangePasswordUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindGetProductsByUserIdUseCase(getProductsByUserIdUseCaseImpl: GetProductsByUserIdUseCaseImpl): GetProductsByUserIdUseCase
}