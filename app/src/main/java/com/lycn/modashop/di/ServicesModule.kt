package com.lycn.modashop.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lycn.modashop.services.firebase.DefaultFirebaseAuthService
import com.lycn.modashop.services.firebase.DefaultUserStoreService
import com.lycn.modashop.services.firebase.AuthService
import com.lycn.modashop.services.firebase.DefaultProductStoreService
import com.lycn.modashop.services.firebase.ProductStoreService
import com.lycn.modashop.services.firebase.UserStoreService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ServicesModule {

    @Provides
    fun provideFirebaseAuthService(): AuthService {
        return DefaultFirebaseAuthService(FirebaseAuth.getInstance())
    }

    @Provides
    fun provideFirebaseUserStoreService(): UserStoreService {
        return DefaultUserStoreService(database = Firebase.firestore)
    }

    @Provides
    fun provideFirebaseProductStoreService(): ProductStoreService {
        return DefaultProductStoreService(database = Firebase.firestore)
    }
}