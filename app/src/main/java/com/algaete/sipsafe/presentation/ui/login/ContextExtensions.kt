package com.algaete.sipsafe.presentation.ui.login

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

fun Context.withLocale(locale: Locale): Context {
    val config = Configuration(resources.configuration)
    Locale.setDefault(locale)
    config.setLocale(locale)
    return createConfigurationContext(config)
}