package com.algaete.sipsafe.presentation.ui.settings

data class Language(val code: String, val name: String)
val supportedLanguages = listOf(
    Language("en", "English"),
    Language("es", "Español"),
    Language("fr", "Français"),
    Language("de", "Deutsch"),
    Language("pt", "Português"),
    Language("ru", "Русский"),
    Language("tr", "Türkçe"),
    Language("vi", "Tiếng Việt"),
    Language("zh-CN", "Chinese (Simplified)"),
    Language("zh-TW", "Chinese (Traditional)"),
    Language("ko", "한국어 (ko)"),
    Language("ja", "日本語 (ja)"), //japanese
    Language("it", "Italiano"),
    Language("id", "Bahasa Indonesia"),
    Language("hi-IN", "हिन्दी (भारत)"),
    Language("bn-BD", " বাংলা (বাংলাদেশ)"),
    Language("ar", " عربي")
    // Add more languages as needed
)