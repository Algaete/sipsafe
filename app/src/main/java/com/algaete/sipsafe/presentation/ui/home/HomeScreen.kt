package com.algaete.sipsafe.presentation.ui.home

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.algaete.sipsafe.R
import com.algaete.sipsafe.presentation.ui.login.LoginViewModel
import com.algaete.sipsafe.presentation.ui.navigation.Routes
import com.algaete.sipsafe.ui.theme.backgroundColor
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.setCustomKeys
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.get
import com.google.firebase.remoteconfig.ktx.remoteConfig


private lateinit var mFirebaseRemoteConfig: FirebaseRemoteConfig
private var welcomeMessage by mutableStateOf("Bienvenidx")
private var isButtonVisible by mutableStateOf(true)

val WELCOME_MESSAGE_KEY = "welcome_message"
val IS_BUTTON_VISIBLE_KEY = "is_button_visible"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigation: NavHostController,
    loginViewModel: LoginViewModel,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
//    analytics.logScreenView(screenName = Routes.HomeScreen.routes)
    val navController = rememberNavController()

    initRemoteConfig()


    val user = homeViewModel.getCurrentUser()

    val context = LocalContext.current


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Image(
                            painter = painterResource(R.drawable.profile),
                            contentDescription = "avatar",
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(40.dp)
                                .clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = if (!user?.displayName.isNullOrEmpty()) "Welcome ${user?.displayName}" else welcomeMessage,
                                fontSize = 20.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(),
                actions = {
                    IconButton(
                        onClick = {
                            navigation.navigate(Routes.SettingsScreen.routes)
                        }
                    ) {
                        Icon(
                            Icons.Filled.Settings,
                            contentDescription = "Go to settings"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            BottomNavGraph(navController = navController, context = context, homeViewModel)
        }
    }
}

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    context: Context,
    homeViewModel: HomeViewModel
) {
    NavHost(navController = navController, startDestination = BottomNavScreen.Main.route) {
        composable(route = BottomNavScreen.Main.route) {
            MainScreen(homeViewModel)
        }
        composable(route = BottomNavScreen.Water.route) {
            WaterReminderScreen(homeViewModel)
        }
        composable(route = BottomNavScreen.Pills.route) {
            PillsReminderScreen(homeViewModel)
        }
    }
}

fun initRemoteConfig() {
    mFirebaseRemoteConfig = Firebase.remoteConfig
    val configSettings: FirebaseRemoteConfigSettings = FirebaseRemoteConfigSettings.Builder()
        .setMinimumFetchIntervalInSeconds(0)
        .build()
    mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings)
    mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

    mFirebaseRemoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
        override fun onUpdate(configUpdate: ConfigUpdate) {
            Log.d("HomeScreen", "Updated keys: " + configUpdate.updatedKeys)
            if (configUpdate.updatedKeys.contains(IS_BUTTON_VISIBLE_KEY) || configUpdate.updatedKeys.contains(
                    WELCOME_MESSAGE_KEY
                )
            ) {
                mFirebaseRemoteConfig.activate().addOnCompleteListener {
                    displayWelcomeMessage()
                }
            }
        }

        override fun onError(error: FirebaseRemoteConfigException) {
            Log.d("HomeScreen", "Updated keys: error: $error")
        }
    })

    fetchWelcome()
}

fun fetchWelcome() {
    mFirebaseRemoteConfig.fetchAndActivate()
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val updated = task.result
                println("Parámetros actualizados: $updated")
            } else {
                println("Fetch failed")
            }
        }
}

fun displayWelcomeMessage() {
    welcomeMessage = mFirebaseRemoteConfig[WELCOME_MESSAGE_KEY].asString()
    isButtonVisible = mFirebaseRemoteConfig[IS_BUTTON_VISIBLE_KEY].asBoolean()
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomNavScreen.Main,
        BottomNavScreen.Pills,
        BottomNavScreen.Water
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBar {
        screens.forEach { screens ->
            if (currentDestination != null) {
                AddItem(
                    screens = screens,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screens: BottomNavScreen,
    currentDestination: NavDestination,
    navController: NavHostController
) {
    NavigationBarItem(
        label = { Text(text = screens.title) },
        icon = { Icon(imageVector = screens.icon, contentDescription = "Icons", tint = backgroundColor) },
        selected = currentDestination.hierarchy?.any {
            it.route == screens.route
        } == true,
        onClick = {
            navController.navigate(screens.route) {
                popUpTo(navController.graph.id)
                launchSingleTop = true
            }
        }
    )
}

sealed class BottomNavScreen(val route: String, val title: String, val icon: ImageVector) {
    object Main : BottomNavScreen(
        route = "main",
        title = "Home",
        icon = Icons.Default.Home
    )

    object Pills : BottomNavScreen(
        route = "pills",
        title = "Pills",
        icon = Icons.Default.Medication
    )

    object Water : BottomNavScreen(
        route = "water",
        title = "Water",
        icon = Icons.Default.WaterDrop
    )
}