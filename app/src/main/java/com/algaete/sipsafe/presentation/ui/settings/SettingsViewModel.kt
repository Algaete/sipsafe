package com.algaete.sipsafe.presentation.ui.settings

import android.content.Context
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    auth: FirebaseAuth,

):ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading


    // Simulando una carga de datos
    init {
        viewModelScope.launch {
            // Simula una tarea de carga, por ejemplo, una llamada a una API
            delay(1000) // 3 segundos de espera
            _isLoading.value = false
        }
    }
    fun getCurrentLanguage(context: Context): String {
        val currentLocale: Locale = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            context.resources.configuration.locales.get(0) // For Android N and above
        } else {
            context.resources.configuration.locale // For older versions
        }
        return currentLocale.language // This will return language code, e.g., "en", "es"
    }

    fun showLoading() {
        _isLoading.value = true
    }

    fun hideLoading() {
        _isLoading.value = false
    }


}