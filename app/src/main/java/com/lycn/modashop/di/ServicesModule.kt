package com.lycn.modashop.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lycn.modashop.services.firebase.DefaultFirebaseAuthService
import com.lycn.modashop.services.firebase.DefaultFirebaseStoreService
import com.lycn.modashop.services.firebase.FirebaseAuthService
import com.lycn.modashop.services.firebase.FirebaseStoreService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ServicesModule {

    @Provides
    fun provideFirebaseAuthService(): FirebaseAuthService {
        return DefaultFirebaseAuthService(FirebaseAuth.getInstance())
    }

    @Provides
    fun provideFirebaseStoreService(): FirebaseStoreService {
        return DefaultFirebaseStoreService(database = Firebase.firestore)
    }
}