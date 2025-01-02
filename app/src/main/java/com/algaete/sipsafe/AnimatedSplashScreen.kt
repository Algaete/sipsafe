package com.algaete.sipsafe

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.algaete.sipsafe.presentation.ui.navigation.Routes
import com.algaete.sipsafe.ui.theme.backgroundColor
import kotlinx.coroutines.delay

@Composable
fun AnimatedSplashScreen(navController: NavHostController, isFirstTime: MutableState<Boolean>) {
    var startAnimation by remember {
        mutableStateOf(false)
    }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 3000)
    )
    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(4000)
        navController.popBackStack()
        if (isFirstTime.value) navController.navigate(Routes.WelcomeScreen.routes) else navController.navigate(
            Routes.LoginScreen.routes
        )

    }
    Splash()
}

@Composable
fun Splash() {
    Box(
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.officiallogo),
                contentDescription = "splash_logo",
                modifier = Modifier
                    .size(300.dp)
            )
            Text(
                text = "Sip Safe",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 48.sp
            )
        }

    }
}