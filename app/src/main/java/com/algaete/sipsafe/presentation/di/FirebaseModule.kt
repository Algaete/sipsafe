package com.algaete.sipsafe.presentation.di

import android.content.Context
import com.algaete.sipsafe.R
import com.algaete.sipsafe.presentation.data.AuthRepository
import com.algaete.sipsafe.presentation.data.AuthRepositoryImpl
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAnalytics(
        @ApplicationContext context: Context
    ): FirebaseAnalytics {
        return FirebaseAnalytics.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton // inyectando la interfaz?
    fun provideUserRepository(): AuthRepository =
        AuthRepositoryImpl(provideFirebaseAuth())

    @Provides
    @Singleton
    fun provideGoogleSignInClient(@ApplicationContext context: Context): GetGoogleIdOption {
        return GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(context.getString(R.string.default_web_client_id))
            .build()
    }

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

}