package com.lycn.modashop.services.firebase

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.lycn.modashop.data.model.Kind
import com.lycn.modashop.data.model.Product
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.lycn.modashop.data.model.Result

class DefaultProductStoreService @Inject constructor(private val database: FirebaseFirestore) :
    ProductStoreService {

    override suspend fun fetchKinds(): Result<List<Kind>> {
        val logTag = "fetchKinds"
        try {
            val result = database.collection("kinds")
                .orderBy("order")
                .get()
                .await()
            val kindResults = result.documents.map { doc ->
                doc.toKind()
            }
            Log.i(logTag, "Size: " + kindResults.size)
            return Result.Success(kindResults)
        } catch (e: Exception) {
            Log.e(logTag, e.message.toString())
            return Result.Error(e)
        }
    }

    override suspend fun fetchProductsByKind(kind: String): Result<List<Product>> {
        val logTag = "fetchProductsByKind: $kind"
        try {
            val kindResult = database.collection("kinds").document(kind)
                .get()
                .await()
            val productList = kindResult.get("products") as? List<*>

            val productRef: List<DocumentReference> = productList?.filterIsInstance<DocumentReference>() ?: listOf()
            val products = productRef.map { ref ->
                val product = ref.get().await()
                product.toProduct()
            }
            Log.i(logTag, "Size: " + products.size)
            return Result.Success(products ?: listOf())
        } catch (e: Exception) {
            Log.e(logTag, e.message.toString())
            return Result.Error(e)
        }
    }

    override suspend fun fetchProductById(productId: String): Result<Product> {
        val logTag = "fetchProductById: $productId"
        try {
            val result = database.collection("products").document(productId)
                .get()
                .await()
            val productResult = result.toProduct()
            Log.i(logTag, "Product: $productResult")
            return Result.Success(productResult)
        } catch (e: Exception) {
            Log.e(logTag, e.message.toString())
            return Result.Error(e)
        }
    }
}

fun DocumentSnapshot.toProduct(): Product {
    return Product(
        id = id,
        name = getString("name")!!,
        price = getLong("price")!!.toInt(),
        imageUrl = getString("imageUrl") ?: "",
        currency = getString("currency")!!
    )
}

fun DocumentSnapshot.toKind(): Kind {
    return Kind(
        name = getString("name") ?: "",
        icon = ""
    )
}