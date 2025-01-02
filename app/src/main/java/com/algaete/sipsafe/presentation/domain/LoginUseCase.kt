package com.algaete.sipsafe.presentation.domain

import com.algaete.sipsafe.presentation.data.AuthRepository
import com.algaete.sipsafe.presentation.ui.login.AuthRes
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val repository: AuthRepository) {
    operator fun invoke(email: String, password: String): Flow<AuthRes<FirebaseUser>> {
        return repository.signIn(email, password)
    }
}

class SignUpCase @Inject constructor(private val repository: AuthRepository) {
    operator fun invoke(email: String, password: String): Flow<AuthRes<FirebaseUser>> {
        return repository.signUp(email, password)
    }
}


