package com.lycn.modashop.data.datasource

import com.lycn.modashop.data.model.Result
import com.lycn.modashop.data.model.LoggedInUser
import com.lycn.modashop.services.firebase.AuthService
import java.io.IOException
import java.util.UUID

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource(private val firebaseAuthService: AuthService) {

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication
            val fakeUser = LoggedInUser(UUID.randomUUID().toString(), "Jane Doe", "abc")
            return Result.Success(fakeUser)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}