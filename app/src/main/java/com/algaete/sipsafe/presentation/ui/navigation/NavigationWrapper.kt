package com.algaete.sipsafe.presentation.ui.navigation

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.algaete.sipsafe.presentation.ui.home.HomeScreen
import com.algaete.sipsafe.presentation.ui.login.ForgotPasswordScreen
import com.algaete.sipsafe.presentation.ui.login.LoginScreen
import com.algaete.sipsafe.presentation.ui.login.LoginViewModel
import com.algaete.sipsafe.presentation.ui.login.SignUpScreen
import com.algaete.sipsafe.presentation.ui.login.WelcomeScreen
import com.algaete.sipsafe.presentation.ui.settings.SelectLanguageScreen
import com.algaete.sipsafe.presentation.ui.settings.SettingsScreen
import com.algaete.sipsafe.presentation.ui.settings.SettingsViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun NavigationWrapper(
    context: Context,
    navController: NavHostController = rememberNavController(),
    auth: FirebaseAuth = Firebase.auth,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val currentUser = auth.currentUser
    Log.i("algaete", "Current user is $currentUser")

    var startDestination = Routes.LoginScreen.routes

    if (currentUser == null) Routes.LoginScreen.routes else Routes.HomeScreen.routes

    val isFirstTime = remember { mutableStateOf(true) }
    // Check if it's the first run
    checkFirstRun(context) { isFirst ->
        isFirstTime.value = isFirst
        if (isFirstTime.value) startDestination = Routes.WelcomeScreen.routes
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Routes.WelcomeScreen.routes) {
            WelcomeScreen(navController)
        }
        composable(Routes.LoginScreen.routes) {
            LoginScreen(navController, loginViewModel)
        }
        composable(Routes.HomeScreen.routes) {
            HomeScreen(navController, loginViewModel)
        }
        composable(Routes.SignUpScreen.routes) {
            SignUpScreen(navController, loginViewModel)
        }
        composable(Routes.ForgotPasswordScreen.routes) {
            ForgotPasswordScreen(navController, loginViewModel)
        }
        composable(Routes.SettingsScreen.routes) {
            SettingsScreen(navController, loginViewModel)
        }
        composable(Routes.SelectLanguageScreen.routes) {
            val settingsViewModel: SettingsViewModel = hiltViewModel()
            SelectLanguageScreen(navController, loginViewModel, settingsViewModel)
        }
    }
}


fun checkFirstRun(context: Context, onResult: (Boolean) -> Unit) {
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    val isFirstRun = sharedPreferences.getBoolean("isFirstRun", true)

    if (isFirstRun) {
        // Set it to false as it is no longer the first run
        sharedPreferences.edit().putBoolean("isFirstRun", false).apply()
    }
    onResult(isFirstRun)
}
