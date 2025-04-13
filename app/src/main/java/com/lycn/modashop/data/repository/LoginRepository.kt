package com.lycn.modashop.data.repository

import com.lycn.modashop.data.model.Result
import com.lycn.modashop.services.firebase.AuthService
import javax.inject.Inject

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository @Inject constructor(
    private val firebaseAuthService: AuthService
) {

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    suspend fun login(email: String, password: String): Result<String> {
        return firebaseAuthService.signInUser(email, password)
    }

    fun isUserLogged(): Boolean {
        return firebaseAuthService.isUserLogged()
    }
}