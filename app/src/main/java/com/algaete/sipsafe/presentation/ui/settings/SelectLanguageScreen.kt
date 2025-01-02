package com.algaete.sipsafe.presentation.ui.settings

import android.app.Activity
import android.app.Activity.OVERRIDE_TRANSITION_OPEN
import android.app.LocaleManager
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import androidx.navigation.NavHostController
import com.algaete.sipsafe.R
import com.algaete.sipsafe.presentation.ui.login.LoginViewModel
import com.algaete.sipsafe.presentation.ui.navigation.LoadingScreen
import com.algaete.sipsafe.presentation.ui.navigation.LoadingScreen2
import com.algaete.sipsafe.ui.theme.NunitoFontFamily
import com.algaete.sipsafe.ui.theme.titleColor
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectLanguageScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    settingsViewModel: SettingsViewModel
) {

    val isLoading by settingsViewModel.isLoading.collectAsState()
    val context = LocalContext.current

    if (isLoading) {
        LoadingScreen()
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row() {
                            Icon(
                                imageVector = Icons.Default.ArrowBackIosNew,
                                contentDescription = "Back",
                                modifier = Modifier.clickable { navController.navigateUp() }
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            Text(
                                stringResource(id = R.string.language_title),
                                fontFamily = NunitoFontFamily,
                                fontSize = 22.sp,
                                fontStyle = FontStyle.Normal,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = titleColor,
                        titleContentColor = Color.White
                    )
                )
            },
            content = { paddingValues ->
                LanguageContent(
                    Modifier.padding(paddingValues),
                    loginViewModel,
                    navController,
                    supportedLanguages,
                    settingsViewModel,
                    context
                )
            }
        )
    }

}

@Composable
fun LanguageContent(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel,
    navController: NavHostController,
    languages: List<Language>,
    settingsViewModel: SettingsViewModel,
    context: Context
) {
    val activity = context as? Activity


    val onLanguageSelected: (language: Language) -> Unit = { language ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java)
                .applicationLocales = LocaleList.forLanguageTags(language.code)
        } else {
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(
                    language.code
                )
            )
        }
        val currentLanguage = settingsViewModel.getCurrentLanguage(context)
        Log.i("algaete", "currentLanguage $currentLanguage")
//        activity?.overrideActivityTransition(OVERRIDE_TRANSITION_OPEN,R.anim.slide_in_right, R.anim.slide_out_left)
        activity?.recreate() // Restart the activity to apply the language change
    }

    var selectedLanguage by remember { mutableStateOf<Language?>(languages.first()) }
    Column(
        modifier = modifier
            .padding(2.dp)
            .verticalScroll(rememberScrollState())
    ) {
        languages.forEach { language ->
            LanguageItem(
                language = language,
                isSelected = language == selectedLanguage,
                onClick = {
                    selectedLanguage = language
                    onLanguageSelected(language)
                }
            )
        }
    }
}

@Composable
fun LanguageItem(language: Language, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 22.dp, horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = language.name,
            fontSize = 20.sp,
            color = Color.Gray,
            modifier = Modifier.weight(1f)
        )
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = titleColor
            )
        }
    }
}

