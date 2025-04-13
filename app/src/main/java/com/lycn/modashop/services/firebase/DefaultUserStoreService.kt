package com.lycn.modashop.services.firebase

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.lycn.modashop.data.model.LoggedInUser
import com.lycn.modashop.data.model.Result
import kotlinx.coroutines.tasks.await

class DefaultUserStoreService(
    private val database: FirebaseFirestore,
) : UserStoreService {
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

    override suspend fun getLoggedInUser(email: String): Result<LoggedInUser> {
        val logTag = "getLoggedInUser $email"
        try {
            val result = database.collection("users")
                .whereEqualTo("email", email)
                .get().await()
            Log.i(logTag, "$result")
            return Result.Success(result.last().toLoggedInUser())
        } catch (e: Exception) {
            Log.e(logTag, e.message.toString())
            return Result.Error(e)
        }
    }
}

fun QueryDocumentSnapshot.toLoggedInUser(): LoggedInUser {
    return LoggedInUser(
        userId = getString("userId") ?: "",
        displayName = getString("name") ?: "",
        email = getString("email") ?: ""
    )
}