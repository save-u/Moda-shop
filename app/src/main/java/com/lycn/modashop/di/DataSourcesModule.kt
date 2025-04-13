package com.lycn.modashop.di

import com.lycn.modashop.data.datasource.LoginDataSource
import com.lycn.modashop.services.firebase.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@Module
@InstallIn(ActivityComponent::class)
object DataSourcesModule {

    @Provides
    fun provideLoginDataSource(firebaseAuthService: AuthService): LoginDataSource {
        return LoginDataSource(firebaseAuthService)
    }
}