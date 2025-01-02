package com.algaete.sipsafe.presentation.data

import com.algaete.sipsafe.presentation.ui.login.AuthRes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override fun signIn(email: String, password: String): Flow<AuthRes<FirebaseUser>> = flow {
        try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            emit(AuthRes.Success(result.user!!))
        } catch (e: Exception) {
            emit(AuthRes.Error(errorMessage = e.toString()))
        }
    }

    override fun signUp(email: String, password: String): Flow<AuthRes<FirebaseUser>> = flow {
        try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            emit(AuthRes.Success(result.user!!))
        } catch (e: Exception) {
            emit(AuthRes.Error(errorMessage = e.toString()))
        }
    }
}