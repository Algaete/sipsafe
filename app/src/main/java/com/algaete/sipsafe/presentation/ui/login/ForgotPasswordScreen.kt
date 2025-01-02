package com.algaete.sipsafe.presentation.ui.login

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.algaete.sipsafe.R
import com.algaete.sipsafe.presentation.ui.navigation.Routes
import com.algaete.sipsafe.ui.theme.Purple40
import kotlinx.coroutines.launch


@Composable
fun ForgotPasswordScreen(
    navigation: NavHostController,
    loginViewModel: LoginViewModel
) {
    //analytics.logScreenView(screenName = Routes.ForgotPasswordScreen.routes)

    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { scaffoldPadding ->
        Box(
            modifier = Modifier
                .padding(scaffoldPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Olvidó su contraseña",
                    style = TextStyle(fontSize = 40.sp, color = Purple40)
                )
                Spacer(modifier = Modifier.height(50.dp))
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 48.dp),
                    label = { Text(text = "Correo electrónico") },
                    value = email,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    onValueChange = {
                        email = it
                        emailError = validateEmail(it)
                    },
                    colors = TextFieldDefaults.colors(
                        unfocusedTrailingIconColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    )
                )
                if (!emailError.isNullOrBlank()) {
                    Text(
                        text = emailError!!,
                        style = MaterialTheme.typography.labelSmall,
                        color = Purple40,
                        modifier = Modifier.padding(horizontal = 48.dp)
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
                Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                    Button(
                        onClick = {
                            scope.launch {
                                when (val res = loginViewModel.resetPassword(email)) {
                                    is AuthRes.Success -> {
//                                      analytics.logButtonClicked(buttonName = "Reset password $email")
//                                      Toast.makeText(context, "Correo enviado", Toast.LENGTH_SHORT).show()
                                        snackbarHostState.showSnackbar(
                                            message = "Correo enviado con exito.",
                                            actionLabel = "Deshacer",
                                            duration = SnackbarDuration.Short
                                        )
                                        navigation.navigate(Routes.LoginScreen.routes)
                                    }

                                    is AuthRes.Error -> {
//                                analytics.logError(error = "Reset password error $email : ${res.errorMessage}")
//                                        Toast.makeText(
//                                            context,
//                                            "Error al enviar el correo",
//                                            Toast.LENGTH_SHORT
//                                        ).show()
                                        snackbarHostState.showSnackbar(
                                            message = "Error al enviar correo, intente nuevamente!",
                                            actionLabel = "Deshacer",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                }
                            }
                        },
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.buttonBackground))
                    ) {
                        Text(text = "Recuperar contraseña")
                    }
                }
            }
        }
    }
}

