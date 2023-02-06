package com.example.appshopping.data.repository

import android.util.Log
import com.example.appshopping.domain.model.main.ProductModel
import com.example.appshopping.domain.repository.MainRepository
import com.example.appshopping.domain.usecase.main.add_product.ProductParam
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val collectionReference: CollectionReference,
) : MainRepository {


    override fun addProduct(productParam: ProductParam) {

    }

    override suspend fun getProducts(): Flow<List<ProductModel>> {
        return flow {
            try {
                val querySnapshot = collectionReference.get().await()
                val list = mutableListOf<ProductModel>()
                for (document in querySnapshot.documents) {
                    val product = document.toObject<ProductModel>()
                    product?.let {
                        list.add(product)
                    }
                }
                if (list.isNotEmpty()) {
                    emit(list)
                }
            } catch (e: Exception) {
                Log.d("Main",e.toString())
            }

        }
    }
}