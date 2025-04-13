package com.lycn.modashop.services.firebase

import com.lycn.modashop.data.model.Result

interface AuthService {
    suspend fun registerUser(email: String, password: String): Result<String>
    suspend fun signInUser(email: String, password: String): Result<String>
}