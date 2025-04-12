package com.lycn.modashop.services.firebase

import com.google.firebase.database.DatabaseReference

class DefaultFirebaseRealtimeService(
    private val database: DatabaseReference,
    private val callback: FirebaseRealtimeServiceCallback
) : FirebaseRealtimeService {
    override fun addUser(userId: String, name: String, email: String, password: String) {
        database.child("users").child(userId)
            .setValue(
                hashMapOf(
                    Pair(first = "name", second = name),
                    Pair(first = "email", second = email),
                    Pair(first = "password", second = password)
                )
            )
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback.onAddUserSuccess(userId)
                } else {
                    task.exception?.let { callback.onAddUserFailed(it) }
                }
            }
    }
}


interface FirebaseRealtimeServiceCallback {
    fun onAddUserSuccess(userId: String)

    fun onAddUserFailed(ex: Exception)
}