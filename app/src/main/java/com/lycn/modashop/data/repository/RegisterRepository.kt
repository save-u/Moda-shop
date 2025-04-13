package com.lycn.modashop.data.repository

import com.lycn.modashop.data.model.Result
import com.lycn.modashop.services.firebase.AuthService
import com.lycn.modashop.services.firebase.UserStoreService
import java.util.UUID
import javax.inject.Inject

class RegisterRepository @Inject constructor(
    private val firebaseAuthService: AuthService,
    private val firebaseStoreService: UserStoreService
) {
    suspend fun register(name: String, email: String, password: String): Result<String> {
        return when (val registerResult = firebaseAuthService.registerUser(email, password)) {
            is Result.Success -> {
                firebaseStoreService.addUser(
                    UUID.randomUUID().toString(),
                    name,
                    email,
                    password
                )
            }
            else -> {
                registerResult
            }
        }
    }
}