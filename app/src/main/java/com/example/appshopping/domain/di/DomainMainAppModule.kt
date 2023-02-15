package com.example.appshopping.domain.di

import com.example.appshopping.domain.usecase.main.add_product.AddProductToCartUseCase
import com.example.appshopping.domain.usecase.main.add_product.AddProductToCartUseCaseImpl
import com.example.appshopping.domain.usecase.main.buy.BuyUseCase
import com.example.appshopping.domain.usecase.main.buy.BuyUseCaseImpl
import com.example.appshopping.domain.usecase.main.delete_product_from_cart.DeleteProductUseCase
import com.example.appshopping.domain.usecase.main.delete_product_from_cart.DeleteProductUseCaseImpl
import com.example.appshopping.domain.usecase.main.change_password.ChangePasswordUseCase
import com.example.appshopping.domain.usecase.main.change_password.ChangePasswordUseCaseImpl
import com.example.appshopping.domain.usecase.main.change_user_data.ChangeUserDataUseCase
import com.example.appshopping.domain.usecase.main.change_user_data.ChangeUserDataUseCaseImpl
import com.example.appshopping.domain.usecase.main.get_products.GetProductsUseCase
import com.example.appshopping.domain.usecase.main.get_products.GetProductsUseCaseImpl
import com.example.appshopping.domain.usecase.main.get_products_by_user_id.GetProductsByUserIdUseCase
import com.example.appshopping.domain.usecase.main.get_products_by_user_id.GetProductsByUserIdUseCaseImpl
import com.example.appshopping.domain.usecase.main.get_single_product.GetProductUseCase
import com.example.appshopping.domain.usecase.main.get_single_product.GetProductUseCaseImpl
import com.example.appshopping.domain.usecase.main.get_user.GetUserDataUseCase
import com.example.appshopping.domain.usecase.main.get_user.GetUserDataUseCaseImpl
import com.example.appshopping.domain.usecase.main.save_user.SaveUserUseCase
import com.example.appshopping.domain.usecase.main.save_user.SaveUserUseCaseImpl
import com.example.appshopping.domain.usecase.main.pay.PayUseCase
import com.example.appshopping.domain.usecase.main.pay.PayUseCaseImpl
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
    abstract fun bindGetUserInfoUseCase(getUserInfoUseCaseImpl: GetUserDataUseCaseImpl): GetUserDataUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindSaveUserUseCase(saveUserUseCaseImpl: SaveUserUseCaseImpl): SaveUserUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindChangeUserInfoUseCase(changeUserInfoUseCaseImpl: ChangeUserDataUseCaseImpl): ChangeUserDataUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindChangePasswordUseCase(changePasswordUseCaseImpl: ChangePasswordUseCaseImpl): ChangePasswordUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindGetProductsByUserIdUseCase(getProductsByUserIdUseCaseImpl: GetProductsByUserIdUseCaseImpl): GetProductsByUserIdUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindChangeProductsInCartUseCase(changeProductsInCartUseCaseImpl: DeleteProductUseCaseImpl): DeleteProductUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindAddProductToCartUseCase(addProductToCartUseCaseImpl: AddProductToCartUseCaseImpl): AddProductToCartUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindPayUseCase(payUseCaseImpl: PayUseCaseImpl): PayUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindBuyUseCase(buyUseCaseImpl: BuyUseCaseImpl): BuyUseCase

}