package com.algaete.sipsafe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.algaete.sipsafe.presentation.ui.login.LoginViewModel
import com.algaete.sipsafe.presentation.ui.navigation.NavigationWrapper
import com.algaete.sipsafe.ui.theme.SipSafeTheme
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // Name file SharedPreferences
    private val PREFERENCES_NAME = "AppPreferences"
    private val FIRST_TIME_KEY = "isFirstTime"
    override fun onCreate(savedInstanceState: Bundle?) {
        // Install the splash screen
        val splashScreen = installSplashScreen()

        // Handle the splash screen duration
        splashScreen.setKeepOnScreenCondition { true }
        super.onCreate(savedInstanceState)

        setContent {
            SipSafeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationWrapper(this)
                }
            }
        }
        // Simulate a loading process
        lifecycleScope.launch {
            delay(500) // Keep splash screen for 1/2 seconds
            // After delay, allow the splash screen to disappear
            splashScreen.setKeepOnScreenCondition { false }
        }
    }
}
