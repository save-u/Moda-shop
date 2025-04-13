package com.lycn.modashop.data.repository

import com.lycn.modashop.data.model.Kind
import com.lycn.modashop.data.model.Product
import com.lycn.modashop.services.firebase.ProductStoreService
import com.lycn.modashop.services.firebase.UserStoreService
import javax.inject.Inject
import com.lycn.modashop.data.model.Result

class ProductRepository @Inject constructor(
    private val productStoreService: ProductStoreService
) {
    suspend fun fetchKinds(): Result<List<Kind>> {
        return productStoreService.fetchKinds();
    }

    suspend fun fetchProductsByKind(kind: String): Result<List<Product>> {
        return productStoreService.fetchProductsByKind(kind)
    }

    suspend fun fetchProductById(productId: String): Result<Product> {
        return productStoreService.fetchProductById(productId)
    }
}