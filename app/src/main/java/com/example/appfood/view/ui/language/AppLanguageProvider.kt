package com.example.appfood.view.ui.language

import android.content.Context
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import java.util.*

@Composable
fun AppLanguageProvider(language: String, content: @Composable () -> Unit) {
    val context = LocalContext.current
    val updatedContext = updateLocale(context, language)
    CompositionLocalProvider(LocalContext provides updatedContext) {
        content()
    }
}

fun updateLocale(context: Context, lang: String): Context {
    val locale = Locale(lang)
    Locale.setDefault(locale)
    val config = Configuration(context.resources.configuration)
    config.setLocale(locale)
    return context.createConfigurationContext(config)
}
