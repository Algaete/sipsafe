package com.algaete.sipsafe.presentation.ui.navigation

sealed class Routes(val routes: String) {
    object WelcomeScreen: Routes("welcome")
    object LoginScreen: Routes("login")
    object SignUpScreen: Routes("signup")
    object HomeScreen: Routes("home")
    object ForgotPasswordScreen: Routes("forgotpassword")
    object SettingsScreen: Routes("settings")
    object SelectLanguageScreen: Routes("selectlanguage")
}