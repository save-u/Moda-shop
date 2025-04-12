package com.lycn.modashop.services.firebase

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.lycn.modashop.data.model.Result
import kotlinx.coroutines.tasks.await

class DefaultFirebaseStoreService(
    private val database: FirebaseFirestore,
) : FirebaseStoreService {
    override suspend fun addUser(
        userId: String,
        name: String,
        email: String,
        password: String
    ): Result<String> {
        val userMap = hashMapOf(
            "useId" to userId,
            "name" to name,
            "email" to email,
            "password" to password
        )
        val logTag = "Add user ${userMap.toString()}"
        try {
            val result = database.collection("users")
                .add(userMap).await()
            Log.i(logTag, result.id.toString())
            return Result.Success(userId)
        } catch (e: Exception) {
            Log.e(logTag, e.message.toString())
            return Result.Error(e)
        }
    }
}


interface FirebaseRealtimeServiceCallback {
    fun onAddUserSuccess(userId: String)

    fun onAddUserFailed(ex: Exception)
}