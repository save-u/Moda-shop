package com.lycn.modashop.di

import com.lycn.modashop.data.repository.LoginRepository
import com.lycn.modashop.data.repository.ProductRepository
import com.lycn.modashop.data.repository.RegisterRepository
import com.lycn.modashop.services.firebase.AuthService
import com.lycn.modashop.services.firebase.ProductStoreService
import com.lycn.modashop.services.firebase.UserStoreService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object RepositoriesModule {

    @Provides
    fun provideRegisterRepo(
        firebaseAuthService: AuthService,
        firebaseStoreService: UserStoreService
    ): RegisterRepository {
        return RegisterRepository(firebaseAuthService, firebaseStoreService)
    }

    @Provides
    fun provideLoginRepository(firebaseAuthService: AuthService): LoginRepository {
        return LoginRepository(firebaseAuthService)
    }

    @Provides
    fun productRepository(productStoreService: ProductStoreService): ProductRepository {
        return ProductRepository(productStoreService)
    }
}