package com.lycn.modashop.services.firebase

import com.lycn.modashop.data.model.Result

interface UserStoreService {
    suspend fun addUser(userId: String, name: String, email: String, password: String): Result<String>
}