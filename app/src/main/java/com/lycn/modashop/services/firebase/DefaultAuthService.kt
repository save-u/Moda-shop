package com.lycn.modashop.services.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.lycn.modashop.data.model.Result
import kotlinx.coroutines.tasks.await

class DefaultFirebaseAuthService(
    private val auth: FirebaseAuth,
) : AuthService {

    override suspend fun registerUser(email: String, password: String): Result<String> {
        val logTag = "Register user $email"
        try {
            val result = auth.createUserWithEmailAndPassword(email, password).await();
            return if (result.user == null) {
                Log.e(logTag, "Unknown error")
                Result.Error(Exception("Unknown error"))
            } else {
                Log.i(logTag, result.credential.toString())
                Result.Success(email)
            }
        } catch (e: Exception) {
            Log.i(logTag, e.message.toString())
            return Result.Error(e)
        }
    }

    override suspend fun signInUser(email: String, password: String): Result<String> {
        val logTag = "Sign in user $email"
        try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            return if (result.user == null) {
                Log.e(logTag, "Unknown error")
                Result.Error(Exception("Unknown error"))
            } else {
                Log.i(logTag, result.credential.toString())
                Result.Success(email)
            }
        } catch (e: Exception) {
            Log.e(logTag, e.message.toString())
            return Result.Error(e)
        }
    }
}

interface FirebaseAuthServiceCallBack {
    fun onRegisterSuccess(userId: String)

    fun onRegisterFailed(ex: Exception?)

    fun onSignInSuccess(useId: String)

    fun onSignInFailed(ex: Exception?)

}