package com.algaete.sipsafe.presentation.ui.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import androidx.navigation.NavHostController
import com.algaete.sipsafe.R
import com.algaete.sipsafe.presentation.ui.navigation.Routes
import com.algaete.sipsafe.ui.theme.NunitoFontFamily
import com.algaete.sipsafe.ui.theme.Purple40
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(
    navigation: NavHostController,
    loginViewModel: LoginViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val authState by loginViewModel.authState.collectAsState(initial = null)
    val credentialManager = CredentialManager.create(context)

    var passwordVisibility by remember {
        mutableStateOf(false)
    }

    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { scaffoldPadding ->
        Box(
            modifier = Modifier
                .padding(scaffoldPadding)
                .fillMaxSize()
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                ClickableText(
                    text = AnnotatedString("¿No tienes una cuenta? Regístrate"),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(40.dp),
                    onClick = {
                        navigation.navigate(Routes.SignUpScreen.routes)
                    },
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Default,
                        textDecoration = TextDecoration.Underline,
                        color = Purple40
                    )
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.officiallogo),
                    contentDescription = "Firebase",
                    modifier = Modifier.size(250.dp)
                )
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = TextStyle(fontSize = 44.sp),
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = NunitoFontFamily
                )
                Spacer(modifier = Modifier.height(30.dp))
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 48.dp),
                    label = { Text(text = "Correo electrónico") },
                    value = email,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    onValueChange = { email = it },
                    colors = TextFieldDefaults.colors(
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 48.dp),
                    label = { Text(text = "Contraseña") },
                    value = password,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    onValueChange = { password = it },
                    colors = TextFieldDefaults.colors(
                        unfocusedTrailingIconColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    ),
                    trailingIcon = {
                        val image = if (passwordVisibility) {
                            Icons.Filled.VisibilityOff
                        } else {
                            Icons.Filled.Visibility
                        }
                        IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                            Icon(imageVector = image, contentDescription = "Show password.")
                        }
                    }, visualTransformation = if (passwordVisibility) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                    Button(
                        onClick = {
                            scope.launch {
                                emailPassSignIn(
                                    email,
                                    password,
                                    loginViewModel,
                                    context,
                                    navigation
                                )
                            }
                        },
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.buttonBackground))
                    ) {
                        Text(text = "Iniciar Sesión".uppercase())
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                ClickableText(
                    text = AnnotatedString("¿Olvidaste tu contraseña?"),
                    onClick = {
                        navigation.navigate(Routes.ForgotPasswordScreen.routes) {
                            popUpTo(Routes.LoginScreen.routes) { inclusive = false }
                        }
                    },
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Default,
                        textDecoration = TextDecoration.Underline,
                        color = Purple40
                    )
                )
                Spacer(modifier = Modifier.height(25.dp))
                Text(text = "-------- o --------", style = TextStyle(color = Color.Gray))
                Spacer(modifier = Modifier.height(25.dp))


                LaunchedEffect(authState) {//usamos para anonymous and singin firerbase normal y google
                    when (authState) {
                        is AuthRes.Success -> {
                            //analytics.logButtonClicked("Click: Iniciar sesion con google, anonimo o email")
                            scope.launch {
                                val snackbarJob = launch {
                                    snackbarHostState.showSnackbar(
                                        message = "Inicio de sesión exitoso.",
                                        actionLabel = null,
                                        duration = SnackbarDuration.Indefinite
                                    )
                                }
                                // Esperar 2 segundos antes de cerrar el Snackbar
                                delay(2000)
                                snackbarHostState.currentSnackbarData?.dismiss()
                                snackbarJob.cancel()
                                navigation.navigate(Routes.HomeScreen.routes) {
                                    popUpTo(Routes.LoginScreen.routes) {
                                        inclusive = true
                                    }
                                }
                            }

                        }
                        is AuthRes.Error -> {
                            //analytics.logError("Error SignIn Incognito or firebase normal: ${(authState as AuthRes.Error).errorMessage}")
                            Log.i("algaete", "authstate error")
                            scope.launch {
                                val snackbarJobError = launch {
                                    snackbarHostState.showSnackbar(
                                        message = "Error al iniciar sesión, intente nuevamente",
                                        actionLabel = "Retry",
                                        duration = SnackbarDuration.Indefinite
                                    )
                                }
                                // Esperar 2 segundos antes de cerrar el Snackbar
                                delay(2000)
                                snackbarHostState.currentSnackbarData?.dismiss()
                                snackbarJobError.cancel()
                                loginViewModel.resetAuthState()
                            }
                        }
                        null -> {
                            //analytics.logError("AuthState is null")
                            Log.i("algaete", "authstate null")
                        }
                    }
                }


                Spacer(modifier = Modifier.height(15.dp))
                SocialMediaButton(
                    onClick = {
                        loginViewModel.signInWithGoogle(credentialManager, context)
                    },
                    text = "Continuar con Google",
                    icon = R.drawable.ic_google,
                    color = Color(0xFFF1F1F1)
                )
                Spacer(modifier = Modifier.height(25.dp))
//        ClickableText(
//            text = AnnotatedString("Forzar cierre Crashlytics"),
//            onClick = {
//                throw RuntimeException("Error forzado desde Loginscreen")
//            },
//            style = TextStyle(
//                fontSize = 14.sp,
//                fontFamily = FontFamily.Default,
//                textDecoration = TextDecoration.Underline,
//                color = Purple40
//            )
//        )

            }
        }
    }


}

fun emailPassSignIn(
    email: String,
    password: String,
    loginViewModel: LoginViewModel,
    context: Context,
    navigation: NavHostController
) {
    if (email.isNotEmpty() && password.isNotEmpty()) {
        loginViewModel.signInWithEmailAndPassword(email, password)
    } else {
        Toast.makeText(context, "Existen campos vacíos", Toast.LENGTH_SHORT).show()
    }
}


@Composable
fun SocialMediaButton(onClick: () -> Unit, text: String, icon: Int, color: Color) {
    var click by remember { mutableStateOf(false) }
    Surface(
        onClick = onClick,
        modifier = Modifier
            .padding(horizontal = 40.dp)
            .clickable { click = !click },
        shape = RoundedCornerShape(50),
        border = BorderStroke(
            width = 1.dp,
            color = if (icon == R.drawable.ic_incognito) color else Color.Gray
        ),
        color = color
    ) {
        Row(
            modifier = Modifier
                .padding(start = 12.dp, end = 16.dp, top = 12.dp, bottom = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                modifier = Modifier.size(24.dp),
                contentDescription = text,
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "$text",
                color = if (icon == R.drawable.ic_incognito) Color.White else Color.Black
            )
            click = true
        }
    }
}