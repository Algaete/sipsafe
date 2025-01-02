package com.algaete.sipsafe.presentation.ui.login

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.algaete.sipsafe.R
import com.algaete.sipsafe.presentation.ui.navigation.Routes
import com.algaete.sipsafe.ui.theme.NunitoFontFamily
import com.algaete.sipsafe.ui.theme.backgroundColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WelcomeScreen(navController: NavHostController) {

    val cardItems = listOf(
        "Welcome1",
        "Welcome2",
        "Welcome3"
    )
    val listState = rememberLazyListState()
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)
    val scope = rememberCoroutineScope() // Remember a coroutine scope
    // Calculate the current page based on scroll offset
    val currentPage by remember {
        derivedStateOf {
            val firstVisibleItemIndex = listState.firstVisibleItemIndex
            val firstVisibleItemScrollOffset = listState.firstVisibleItemScrollOffset
            // If scrolled more than half the width of the item, move to the next item
            if (firstVisibleItemScrollOffset > listState.layoutInfo.visibleItemsInfo.firstOrNull()?.size ?: 0 / 2) {
                firstVisibleItemIndex + 1
            } else {
                firstVisibleItemIndex
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyRow(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .weight(1f),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            flingBehavior = flingBehavior
        ) {
            items(cardItems) { item ->
                // Page Indicators
                CarouselCard(item)
            }
        }
        // Page Indicators
        PageIndicators(
            totalItems = cardItems.size,
            currentPage = currentPage
        )

        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .padding(40.dp, 0.dp, 40.dp, 0.dp)
                .padding(bottom = 96.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = {
                    // Scroll to the next item, if it exists
                    val nextPage = currentPage + 1
                    if (nextPage < cardItems.size) {
                        // Scroll to next item
                        scope.launch {
                            listState.animateScrollToItem(nextPage)
                        }
                    } else {
                        // Navigate to login screen and clear back stack
                        navController.navigate(Routes.LoginScreen.routes) {
                            popUpTo(Routes.WelcomeScreen.routes) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.buttonBackground))
            ) {
                Text(text = "Next".uppercase())
            }
        }
    }
}

@Composable
fun CarouselCard(content: String) {

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    Card(
        colors = CardDefaults.cardColors(backgroundColor),
        modifier = Modifier
            .width(screenWidth - 32.dp)  // Ajusta el ancho de las tarjetas
            .padding(vertical = 16.dp),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                when(content){
                    "Welcome1"->{
                        Text(
                            text = "Welcome to Your Wellness Companion!",
                            fontSize = 40.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            lineHeight = 36.sp,
                            fontFamily = NunitoFontFamily
                        )
                        Row(
                            modifier = Modifier
                                .padding(top = 32.dp)
                                .padding(end = 16.dp)
                        ) {
                            Image(
                                modifier = Modifier
                                    .width(150.dp)
                                    .height(250.dp),
                                painter = painterResource(R.drawable.sipsafi),
                                contentDescription = "sipsafi",
                                contentScale = ContentScale.Crop
                            )
                            Text(
                                text = "Stay hydrated and never miss a pill again! Our app is designed to help you track your water intake and remind you when it's time to take your medication.",
                                fontSize = 20.sp, fontWeight = FontWeight.Normal,
                                fontFamily = FontFamily.Default,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.align(alignment = Alignment.CenterVertically)

                            )
                        }
                    }
                    "Welcome2" -> {
                        Image(
                            modifier = Modifier
                                .width(300.dp)
                                .height(300.dp),
                            painter = painterResource(R.drawable.sipsafi_water),
                            contentDescription = "sidadi",
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = "Hydration Made Simple",
                            fontSize = 40.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            lineHeight = 36.sp,
                            fontFamily = NunitoFontFamily
                        )
                        Text(
                            text = "Track your daily water consumption effortlessly. Set your daily hydration goal, and we'll remind you to drink water at regular intervals.",
                            fontSize = 20.sp, fontWeight = FontWeight.Normal,
                            fontFamily = FontFamily.Default,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                    else -> {
                        Image(
                            modifier = Modifier
                                .width(300.dp)
                                .height(300.dp),
                            painter = painterResource(R.drawable.pills),
                            contentDescription = "sipsafi",
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = "Never Forget Your Medication",
                            fontSize = 40.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            lineHeight = 36.sp,
                            fontFamily = NunitoFontFamily
                        )
                        Text(
                            text = "Set up reminders for all your medications in a few taps. Whether it's a daily pill or a one-time prescription, we've got you covered.",
                            fontSize = 20.sp, fontWeight = FontWeight.Normal,
                            fontFamily = FontFamily.Default,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("ResourceAsColor")
@Composable
fun PageIndicators(totalItems: Int, currentPage: Int) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        repeat(totalItems) { index ->
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .size(12.dp)
                    .background(
                        color = if (index == currentPage) Color(color = R.color.buttonBackground) else Color.White,
                        shape = CircleShape
                    )
            )
        }
    }
}
