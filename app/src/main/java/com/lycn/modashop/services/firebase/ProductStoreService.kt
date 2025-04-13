package com.lycn.modashop.services.firebase

import com.lycn.modashop.data.model.Kind
import com.lycn.modashop.data.model.Product
import com.lycn.modashop.data.model.Result

interface ProductStoreService {

    suspend fun fetchKinds(): Result<List<Kind>>

    suspend fun fetchProductsByKind(kind: String): Result<List<Product>>

    suspend fun fetchProductById(productId: String): Result<Product>
}