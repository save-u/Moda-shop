package com.lycn.modashop.di

import com.lycn.modashop.data.datasource.LoginDataSource
import com.lycn.modashop.services.firebase.FirebaseAuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(ActivityComponent::class)
object DataSourcesModule {

    @Provides
    fun provideLoginDataSource(firebaseAuthService: FirebaseAuthService): LoginDataSource {
        return LoginDataSource(firebaseAuthService)
    }
}