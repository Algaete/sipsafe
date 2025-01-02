package com.algaete.sipsafe.presentation.ui.login

import android.content.Context
import android.util.Log
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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.algaete.sipsafe.R
import com.algaete.sipsafe.ui.theme.Purple40
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    navigation: NavHostController,
    loginViewModel: LoginViewModel
) {

    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }

    var password by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf<String?>(null) }

    val authState by loginViewModel.authState.collectAsState()

    val scope = rememberCoroutineScope()

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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Crear Cuenta",
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
                    maxLines = 1,
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
                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 48.dp),
                    label = { Text(text = "Contraseña") },
                    value = password,
                    colors = TextFieldDefaults.colors(
                        unfocusedTrailingIconColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    ),
                    visualTransformation = if (passwordVisibility) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    onValueChange = {
                        password = it
                        passwordError = validatePassword(it)
                    },
                    trailingIcon = {
                        val image = if (passwordVisibility) {
                            Icons.Filled.VisibilityOff
                        } else {
                            Icons.Filled.Visibility
                        }
                        IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                            Icon(imageVector = image, contentDescription = "Show password.")
                        }
                    },
                    maxLines = 1
                )
                if (!passwordError.isNullOrBlank()) {
                    Text(
                        text = passwordError!!,
                        style = MaterialTheme.typography.labelSmall,
                        color = Purple40,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))
                Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                    Button(
                        onClick = {
                            scope.launch {
                                emailError = validateEmail(email)
                                passwordError = validatePassword(password)
                                if (emailError.isNullOrBlank() && passwordError.isNullOrBlank()) {
                                    signUp(email, password, loginViewModel)
                                }

                            }
                        },
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.buttonBackground))
                    ) {
                        Text(text = "Registrarse")
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))
                ClickableText(
                    text = AnnotatedString("¿Ya tienes cuenta? Inicia sesión"),
                    onClick = {
                        navigation.popBackStack()
                    },
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Default,
                        textDecoration = TextDecoration.Underline,
                        color = Purple40
                    )
                )
            }

            LaunchedEffect(authState) {
                when (authState) {
                    is AuthRes.Success -> {
//                        Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Usuario creado con exito.",
                                actionLabel = null,
                                duration = SnackbarDuration.Short
                            )
                            loginViewModel.signOut()
                            navigation.popBackStack()
                        }

                    }

                    is AuthRes.Error -> {
                        //analytics.logButtonClicked("Error SignUp: ${(authState as AuthRes.Error).errorMessage}")
//                        Toast.makeText(
//                            context,
//                            "Error SignUp: ${(authState as AuthRes.Error).errorMessage}",
//                            Toast.LENGTH_SHORT
//                        ).show()
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Error al crear usuario, intente nuevamente!",
                                actionLabel = "Retry",
                                duration = SnackbarDuration.Short
                            )
                        }

                    }

                    null -> Log.i("algaete", "No action yet")
                }
            }
        }
    }

}

fun validatePassword(password: String): String? {
    if (password.length < 6) {
        return "Password must be at least 6 characters long."
    } else if (!password.any { it.isUpperCase() }) {
        return "Password must include at least one uppercase letter (A-Z)."
    }
    return ""
}

fun validateEmail(it: String): String? {
    if (!Patterns.EMAIL_ADDRESS.matcher(it).matches()) {
        return "Please enter a valid email address (e.g., user@example.com)."
    }
    return ""
}

private fun signUp(
    email: String,
    password: String,
    loginViewModel: LoginViewModel
) {
    loginViewModel.createUserWithEmailAndPassword(email, password)
}
