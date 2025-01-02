package com.algaete.sipsafe.presentation.ui.settings

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.algaete.sipsafe.presentation.ui.login.LoginViewModel
import com.algaete.sipsafe.presentation.ui.navigation.Routes
import com.algaete.sipsafe.ui.theme.NunitoFontFamily
import com.algaete.sipsafe.ui.theme.backgroundColor
import com.algaete.sipsafe.ui.theme.lightBlue
import com.algaete.sipsafe.ui.theme.titleColor
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navigation: NavHostController, loginViewModel: LoginViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Settings",
                        fontFamily = NunitoFontFamily,
                        fontSize = 22.sp,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = titleColor,
                    titleContentColor = Color.White
                )
            )
        },
        content = { paddingValues ->
            SettingsContent(Modifier.padding(paddingValues), loginViewModel, navigation)
        }
    )
}

@Composable
fun SettingsContent(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel,
    navigation: NavHostController
) {
    val scrollState = rememberScrollState()
    var notificationsEnabled by remember { mutableStateOf(true) }
    var darkThemeEnabled by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var showDialogGender by remember { mutableStateOf(false) }
    var showDialogWeight by remember { mutableStateOf(false) }

    // State to keep track of the selected option
    var selectedOption by remember { mutableStateOf("Option 1") }


    val onLogoutConfirmed: () -> Unit = {
        loginViewModel.signOut()

        navigation.popBackStack(
            route = Routes.LoginScreen.routes,
            inclusive = true
        )
        navigation.navigate(Routes.LoginScreen.routes)
    }

    val onGenderConfirmed: () -> Unit = {

    }
    val onWeightConfirmed: () -> Unit = {

    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // App Settings Section
        Text(
            text = "General Settings",
            color = titleColor,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = NunitoFontFamily,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        SettingsToggleItem(
            icon = Icons.Default.Notifications,
            title = "Enable Notifications",
            checked = notificationsEnabled,
            onCheckedChange = { notificationsEnabled = it }
        )

        SettingsItemText(
            icon = Icons.Default.Language,
            title = "Language",
            text = "Español",
            onClick = { navigation.navigate(Routes.SelectLanguageScreen.routes) }
        )

        SettingsToggleItem(
            icon = Icons.Default.DarkMode,
            title = "Dark Theme",
            checked = darkThemeEnabled,
            onCheckedChange = { darkThemeEnabled = it }
        )


        HorizontalDivider(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(0.5f),
            color = lightBlue,
            thickness = 1.dp
        )
        // Account Settings Section
        Text(
            text = "User Settings",
            color = titleColor,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = NunitoFontFamily,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        SettingsTextIconItem(
            icon = Icons.Default.Person,
            title = "Profile",
            onClick = { /* Navigate to Profile Screen */ }
        )
        SettingsTextItem(
            title = "Gender",
            text = "Male",
            onClick = { showDialogGender = true }
        )
        SettingsTextItem(
            title = "Weight",
            text = "68 Kg",
            onClick = { showDialogWeight = true }
        )
        SettingsTextItem(
            title = "Somatotype",
            text = "Ectomorfo",
            onClick = { /* Navigate to Change Password Screen */ }
        )
        SettingsTextIconItem(
            icon = Icons.Default.ExitToApp,
            title = "Logout",
            onClick = { showDialog = true }
        )
        HorizontalDivider(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(0.5f),
            color = lightBlue,
            thickness = 1.dp
        )
        // Account Settings Section
        Text(
            text = "Others",
            color = titleColor,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = NunitoFontFamily,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        SettingsItem(
            icon = Icons.Default.Info,
            title = "About",
            onClick = { /* Navigate to About Screen */ }
        )

        SettingsItem(
            icon = Icons.Default.PrivacyTip,
            title = "Privacy Policy",
            onClick = { /* Open Privacy Policy */ }
        )

        if (showDialog) {
            LogoutDialog(onConfirmLogout = {
                onLogoutConfirmed()
                showDialog = false
            }, onDismiss = { showDialog = false })
        }
        if (showDialogGender) {
            GenderDialog(onConfirmGender = {
                selectedOption = it
                showDialogGender = false
            }, onDismissRequest = { showDialogGender = false })
        }
        if (showDialogWeight) {
            WeightDialog(onConfirmWeight = {
                showDialogWeight = false
            }, onDismissRequest = { showDialogWeight = false })
        }
    }
}


@Composable
fun SettingsTextIconItem(// icon text and text
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = Color.Gray,
            modifier = Modifier.size(24.dp),
        )
    }
}

@Composable
fun SettingsItemText( // icon text and text
    icon: ImageVector,
    title: String,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = Color.Gray,
            modifier = Modifier.size(24.dp),
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        Text(text = text, fontWeight = FontWeight.ExtraBold, color = titleColor)
    }
}


@Composable
fun SettingsItem( // icon text and icon
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = Color.Gray,
            modifier = Modifier.size(24.dp),
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun SettingsTextItem(// text and text
    title: String,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = title,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        Text(text = text, fontWeight = FontWeight.ExtraBold, color = titleColor)

    }
}

@Composable
fun SettingsToggleItem(
    icon: ImageVector,
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = Color.Gray,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = titleColor,
                checkedTrackColor = lightBlue,
                checkedBorderColor = Color.Transparent,
                uncheckedBorderColor = Color.Transparent
            )
        )
    }
}


@Composable
fun LogoutDialog(onConfirmLogout: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        title = {
            Text(
                "Cerrar sesión",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = NunitoFontFamily,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        },
        text = { Text("¿Estás seguro que deseas cerrar sesión?") },
        confirmButton = {
            TextButton(
                onClick = onConfirmLogout
            ) {
                Text("Aceptar", fontSize = 18.sp, color = titleColor, fontFamily = NunitoFontFamily)
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    "Cancelar",
                    fontSize = 18.sp,
                    color = Color.Gray,
                    fontFamily = NunitoFontFamily
                )
            }
        }
    )
}

@Composable
fun GenderDialog(
    onDismissRequest: () -> Unit,
    onConfirmGender: (String) -> Unit
) {
    val options = listOf("Male", "Female")
    var selectedOption by remember { mutableStateOf(options[0]) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        containerColor = Color.White,
        title = {
            Text(
                "Gender",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = NunitoFontFamily,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        },
        text = {
            Column {
                options.forEach { option ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedOption = option }
                            .padding(4.dp)
                    ) {
                        RadioButton(
                            selected = selectedOption == option,
                            onClick = { selectedOption = option },
                            colors = RadioButtonColors(
                                selectedColor = backgroundColor,
                                unselectedColor = Color.LightGray,
                                disabledSelectedColor = backgroundColor,
                                disabledUnselectedColor = backgroundColor
                            )
                        )
                        Text(
                            text = option,
                            modifier = Modifier.padding(start = 4.dp),
                            fontSize = 16.sp
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmGender(selectedOption)
                    onDismissRequest()
                }
            ) {
                Text(
                    "OK", fontSize = 18.sp, color = titleColor, fontFamily = NunitoFontFamily
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Cancel", fontSize = 18.sp, color = Color.Gray, fontFamily = NunitoFontFamily)
            }
        }
    )
}

@Composable
fun WeightDialog(onConfirmWeight: () -> Unit, onDismissRequest: () -> Unit) {
    var selectedValue by remember { mutableStateOf(50) }
    AlertDialog(
        onDismissRequest = onDismissRequest,
        containerColor = Color.White,
        title = {
            Text(
                "Weight",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = NunitoFontFamily,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmWeight()
                    onDismissRequest()
                }
            ) {
                Text("OK", fontSize = 18.sp, color = titleColor, fontFamily = NunitoFontFamily)
            }
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(contentAlignment = Alignment.CenterEnd) {
                    Material3NumberPickerWithSlider(
                        value = selectedValue,
                        range = 0..200,
                        onValueChange = { newValue ->
                            selectedValue = newValue
                        }
                    )
                }

            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Cancel", fontSize = 18.sp, color = Color.Gray, fontFamily = NunitoFontFamily)
            }
        }
    )
}


@Composable
fun Material3NumberPickerWithSlider(
    value: Int,
    range: IntRange,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Material3NumberPicker(
            value = value,
            range = range,
            onValueChange = onValueChange
        )
        Spacer(modifier = Modifier.height(16.dp))
        Slider(
            value = value.toFloat(),
            onValueChange = { onValueChange(it.roundToInt()) },
            valueRange = range.first.toFloat()..range.last.toFloat(),
            steps = range.last - range.first - 1,
            colors = SliderDefaults.colors(
                activeTrackColor = titleColor,
                activeTickColor = titleColor,
                inactiveTrackColor = lightBlue,
                inactiveTickColor = lightBlue,
                disabledThumbColor = lightBlue,
                disabledActiveTrackColor = lightBlue,
                thumbColor = titleColor,
                disabledActiveTickColor = lightBlue,
                disabledInactiveTickColor = lightBlue,
                disabledInactiveTrackColor = lightBlue,

                )
        )
    }
}


@Composable
fun Material3NumberPicker(
    value: Int,
    range: IntRange,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        // Botón para disminuir el valor
        IconButton(
            onClick = {
                if (value > range.first) onValueChange(value - 1)
            },
            enabled = value > range.first,
            colors = IconButtonDefaults.iconButtonColors(contentColor = Color.LightGray)
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "Disminuir valor"
            )
        }

        // Mostrar el valor actual
        Text(
            text = value.toString(),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(horizontal = 4.dp),
            fontSize = 34.sp,
            color = titleColor,
            fontFamily = NunitoFontFamily
        )
        Text(
            text = "kg", fontSize = 14.sp,
            fontFamily = NunitoFontFamily
        )


        // Botón para aumentar el valor
        IconButton(
            onClick = {
                if (value < range.last) onValueChange(value + 1)
            },
            enabled = value < range.last,
            colors = IconButtonDefaults.iconButtonColors(contentColor = Color.LightGray)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Aumentar valor"
            )
        }
    }
}