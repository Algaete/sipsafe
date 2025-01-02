package com.algaete.sipsafe.presentation.data

import com.algaete.sipsafe.presentation.ui.login.AuthRes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface AuthRepository {
    fun signIn(email: String, password: String): Flow<AuthRes<FirebaseUser>>
    fun signUp(email: String, password: String): Flow<AuthRes<FirebaseUser>>
}
