package com.lycn.modashop.services.firebase

import com.google.firebase.firestore.auth.User

interface FirebaseRealtimeService {
    fun addUser(userId: String, name: String, email: String, password: String)
}