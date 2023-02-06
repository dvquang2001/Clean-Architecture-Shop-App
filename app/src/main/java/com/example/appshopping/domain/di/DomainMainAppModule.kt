package com.example.appshopping.domain.di

import com.example.appshopping.domain.usecase.main.get_product.GetProductUseCase
import com.example.appshopping.domain.usecase.main.get_product.GetProductUseCaseImpl
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
    abstract fun bindGetProductsUseCase(getProductUseCaseImpl: GetProductUseCaseImpl): GetProductUseCase
}