package com.algaete.sipsafe.presentation.ui.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.algaete.sipsafe.presentation.domain.SignInUseCase
import com.algaete.sipsafe.presentation.domain.SignUpCase
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val signInUseCase: SignInUseCase,
    private val signUpCase: SignUpCase,
    private val googleIdOption: GetGoogleIdOption
) : ViewModel() {
    // Use StateFlow to hold the authentication state
    private val _authState = MutableStateFlow<AuthRes<FirebaseUser>?>(null)
    val authState: StateFlow<AuthRes<FirebaseUser>?> = _authState


    fun signInAnonymously() {
        viewModelScope.launch {
            try {
                val result = auth.signInAnonymously().await()
                val user = result.user
                // Handle the case where the user is null
                if (user == null) {
                    Log.e("AuthError", "Result.user is null")
                    _authState.value = AuthRes.Error("Authentication failed, user is null")
                } else {
                    Log.i("AuthSuccess", "User signed in: ${user.uid}")
                    _authState.value = AuthRes.Success(user)
                    Log.i("AuthSuccess", "Auth state value ${authState.value}")
                }
            } catch (e: Exception) {
                _authState.value = AuthRes.Error(e.message ?: "Error al iniciar sesión")
            }
        }
    }

    fun signOut() {
        auth.signOut()
        _authState.value = null
        Log.i("AuthSuccess", "Auth state value after signout ${authState.value}")
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun signInWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            signInUseCase(email, password).collect { result ->
                _authState.value = result
                Log.i("Algaete", "Login viewmodel auth state false: ${authState.value}")
            }
        }
    }

    fun createUserWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            signUpCase(email, password).collect { result ->
                _authState.value = result
                Log.i("Algaete", "El result del registro es  $result")
            }
        }
    }

    suspend fun resetPassword(email: String): AuthRes<Unit> {
        return try {
            auth.sendPasswordResetEmail(email).await()
            AuthRes.Success(Unit)
        } catch (e: Exception) {
            AuthRes.Error(e.message ?: "Error al restablecer la contraseña")
        }
    }

    fun resetAuthState() {
        _authState.value = null
    }

    fun signInWithGoogle(credentialManager: CredentialManager, context: Context) {
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        viewModelScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = context
                )

                val credential = result.credential
                val googleIdTokenCredential =
                    GoogleIdTokenCredential.createFrom(credential.data)
                val googleIdToken = googleIdTokenCredential.idToken

                val firebaseCredential =
                    GoogleAuthProvider.getCredential(googleIdToken, null)
                val firebaseUser = auth.signInWithCredential(firebaseCredential).await()
                firebaseUser.user?.let {
                    _authState.value = AuthRes.Success(it)
                } ?: throw Exception("Sign in with Google failed.")

            } catch (e: GetCredentialException) {
                _authState.value = AuthRes.Error(e.message ?: "Sign in with Google failed.")
                Log.i("error google credential", "Error ${e.message}")
//                Toast.makeText(context, "Error ${e.message}", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }

}