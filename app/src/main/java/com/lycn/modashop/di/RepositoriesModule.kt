package com.lycn.modashop.di

import com.lycn.modashop.data.datasource.LoginDataSource
import com.lycn.modashop.data.repository.LoginRepository
import com.lycn.modashop.data.repository.RegisterRepository
import com.lycn.modashop.services.firebase.FirebaseAuthService
import com.lycn.modashop.services.firebase.FirebaseStoreService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import kotlin.math.log

@Module
@InstallIn(ActivityComponent::class)
object RepositoriesModule {

    @Provides
    fun provideRegisterRepo(
        firebaseAuthService: FirebaseAuthService,
        firebaseStoreService: FirebaseStoreService
    ): RegisterRepository {
        return RegisterRepository(firebaseAuthService, firebaseStoreService)
    }

    @Provides
    fun provideLoginRepository(firebaseAuthService: FirebaseAuthService): LoginRepository {
        return LoginRepository(firebaseAuthService)
    }
}