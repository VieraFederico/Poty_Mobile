//package hci.mobile.poty.screens.register
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Button
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.text.input.VisualTransformation
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import hci.mobile.poty.R
//import hci.mobile.poty.ui.theme.PotyTheme
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.layout.ContentScale
//import hci.mobile.poty.utils.ErrorMessage
//import hci.mobile.poty.utils.TextFieldWithLabel
//import androidx.lifecycle.viewmodel.compose.viewModel
//import hci.mobile.poty.utils.CompactDateFieldWithLabel
//import hci.mobile.poty.utils.ThickTextFieldWithLabel
//
//@Composable
//fun RegistrationScreen(
//    viewModel: RegistrationViewModel = viewModel(),
//    onRegisterSuccess: () -> Unit, // Esto puede llevar al Dashboard
//    onNavigateToLogin: () -> Unit
//) {
//    val state by viewModel.state.collectAsState()
//    val isRegistrationSuccessful by viewModel.isRegistrationSuccessful.collectAsState()
//
//    // Si el registro es exitoso, navega al Dashboard o Login
//    LaunchedEffect(isRegistrationSuccessful) {
//        if (isRegistrationSuccessful) {
//            onNavigateToLogin()
//        }
//    }
//
//    PotyTheme(darkTheme = true, dynamicColor = false) {
//        Scaffold(
//            modifier = Modifier.fillMaxSize(),
//            containerColor = MaterialTheme.colorScheme.onBackground,
//        ) { innerPadding ->
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(innerPadding),
//                verticalArrangement = Arrangement.Top
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.loginreg),
//                    contentDescription = "Foto Landing",
//                    modifier = Modifier.fillMaxWidth(),
//                    contentScale = ContentScale.Crop
//                )
//
//                Text(
//                    text = if (state.currentStep < 3) "Registrarse" else "¡Ya Casi!",
//                    style = MaterialTheme.typography.titleLarge,
//                    modifier = Modifier
//                        .padding(top = 20.dp, bottom = 10.dp)
//                        .align(Alignment.CenterHorizontally)
//                )
//
//                when (state.currentStep) {
//                    1 -> StepOne(
//                        name = state.name,
//                        surname = state.surname,
//                        birthDate = state.birthday,
//                        onNameChange = { viewModel.onEvent(RegistrationEvent.UpdateName(it)) },
//                        onSurnameChange = { viewModel.onEvent(RegistrationEvent.UpdateSurname(it)) },
//                        onBirthdayChange = { viewModel.onEvent(RegistrationEvent.UpdateBirthday(it)) },
//                        onNext = { viewModel.onEvent(RegistrationEvent.NextStep) },
//                        errorMessage = state.errorMessage
//                    )
//                    2 -> StepTwo(
//                        email = state.email,
//                        password = state.password,
//                        onEmailChange = { viewModel.onEvent(RegistrationEvent.UpdateEmail(it)) },
//                        onPasswordChange = { viewModel.onEvent(RegistrationEvent.UpdatePassword(it)) },
//                        onNext = { viewModel.onEvent(RegistrationEvent.NextStep) },
//                        errorMessage = state.errorMessage
//                    )
//                    3 -> StepThree(
//                        confirmationCode = state.confirmationCode,
//                        email = state.email,
//                        onConfirmationCodeChange = { viewModel.onEvent(RegistrationEvent.UpdateConfirmationCode(it)) },
//                        onSubmit = { viewModel.onEvent(RegistrationEvent.Submit) },
//                        errorMessage = state.errorMessage
//                    )
//                }
//
//                if (state.isLoading) {
//                    CircularProgressIndicator(
//                        modifier = Modifier.align(Alignment.CenterHorizontally)
//                    )
//                }
//
//                if (state.currentStep > 1) {
//                    TextButton(
//                        onClick = { viewModel.onEvent(RegistrationEvent.PreviousStep) },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(50.dp)
//                            .align(Alignment.CenterHorizontally)
//                    ) {
//                        Text(
//                            text = "Volver para atrás",
//                            style = MaterialTheme.typography.bodyLarge,
//                            color = MaterialTheme.colorScheme.secondary
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
//
//
//
//@Composable
//fun StepOne(
//    name: String,
//    surname: String,
//    birthDate: String,
//    onBirthdayChange: (String) -> Unit,
//    onNameChange: (String) -> Unit,
//    onSurnameChange: (String) -> Unit,
//    onNext: () -> Unit,
//    errorMessage: String
//) {
//    var localName by remember { mutableStateOf(name) }
//    var localSurname by remember { mutableStateOf(surname) }
//    var localBirthDate by remember { mutableStateOf(birthDate) }
//
//    Column(modifier = Modifier.padding(15.dp)) {
//
//        TextFieldWithLabel(
//            label = "Nombre",
//            value = localName,
//            onValueChange = {
//                localName = it
//                onNameChange(it)
//            }
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        TextFieldWithLabel(
//            label = "Apellido",
//            value = localSurname,
//            onValueChange = {
//                localSurname = it
//                onSurnameChange(it)
//            }
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//
//        CompactDateFieldWithLabel(
//            label = "Fecha de Nacimiento (DD/MM/AAAA)",
//            value = localBirthDate,
//            onValueChange = {
//                localBirthDate = it
//                onBirthdayChange(it)
//            }
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//        Button(
//            onClick = { onNext() },
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(50.dp)
//        ) {
//            Text(text = "Siguiente", color = MaterialTheme.colorScheme.onBackground)
//        }
//        if (errorMessage.isNotEmpty()) {
//            ErrorMessage(message = errorMessage)
//        }
//    }
//}
//
//@Composable
//fun StepTwo(
//    email: String,
//    password: String,
//    onEmailChange: (String) -> Unit,
//    onPasswordChange: (String) -> Unit,
//    onNext: () -> Unit,
//    errorMessage: String
//) {
//    var localEmail by remember { mutableStateOf(email) }
//    var localPassword by remember { mutableStateOf(password) }
//    var localPasswordCheck by remember { mutableStateOf(password) }
//
//    Column(modifier = Modifier.padding(15.dp)) {
//        TextFieldWithLabel(
//            label = "Correo Electrónico",
//            value = localEmail,
//            onValueChange = {
//                localEmail = it
//                onEmailChange(it)
//            }
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        TextFieldWithLabel(
//            label = "Contraseña",
//            value = localPassword,
//            onValueChange = {
//                localPassword = it
//                onPasswordChange(it)
//            },
//            isPassword = true
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        TextFieldWithLabel(
//            label = "Confirmar Contraseña",
//            value = localPasswordCheck,
//            onValueChange = {
//                localPasswordCheck = it
//            },
//            isPassword = true
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Button(
//            onClick = { onNext() },
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(50.dp)
//        ) {
//            Text(text = "Siguiente", color = MaterialTheme.colorScheme.onBackground)
//        }
//
//        if (errorMessage.isNotEmpty()) {
//            ErrorMessage(message = errorMessage)
//        }
//    }
//}
//
//
//
//
//@Composable
//fun StepThree(
//    confirmationCode: String,
//    email: String,
//    onConfirmationCodeChange: (String) -> Unit,
//    onSubmit: () -> Unit,
//    errorMessage: String
//) {
//    var localConfirmationCode by remember { mutableStateOf(confirmationCode) }
//
//    Column(
//        modifier = Modifier.padding(15.dp)
//    ) {
//        Text(
//            text = "Para finalizar tu registro, ingresá el código que fue enviado a:",
//            modifier = Modifier.align(Alignment.CenterHorizontally)
//        )
//
//        Text(
//            text = email,
//            modifier = Modifier.align(Alignment.CenterHorizontally)
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        ThickTextFieldWithLabel(
//            value = localConfirmationCode,
//            onValueChange = { localConfirmationCode = it },
//            isPassword = false
//        )
//
//        if (errorMessage.isNotEmpty()) {
//            Text(
//                text = errorMessage,
//                color = Color.Red,
//                style = MaterialTheme.typography.bodySmall,
//                modifier = Modifier.padding(top = 8.dp)
//            )
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Button(
//            onClick = {
//                onConfirmationCodeChange(localConfirmationCode)
//                onSubmit()
//            },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text(
//                text = "Registrarse",
//                color = MaterialTheme.colorScheme.onBackground
//            )
//        }
//    }
//}
//
//
//
//@Preview
//@Composable
//fun RegisterScreenPreview() {
//    RegistrationScreen(
//        onRegisterSuccess = {},
//        onNavigateToLogin = {}
//    )
//}




package hci.mobile.poty.screens.register

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import hci.mobile.poty.ui.theme.PotyTheme
import hci.mobile.poty.utils.*
import androidx.lifecycle.viewmodel.compose.viewModel
import hci.mobile.poty.ui.components.LoginRegisterImageSection
import hci.mobile.poty.utils.isLandscape
import hci.mobile.poty.utils.isTablet

@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel = viewModel(),
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    mockWindowSizeClass: WindowSizeClass? = null
) {
    val state by viewModel.state.collectAsState()
    val isRegistrationSuccessful by viewModel.isRegistrationSuccessful.collectAsState()
    val windowSizeClass = mockWindowSizeClass ?: calculateWindowSizeClass()
    val isLandscape = windowSizeClass.isLandscape()
    val contentPadding = if (windowSizeClass.isTablet()) 32.dp else if(!windowSizeClass.isLandscape()) 16.dp else 8.dp

    // Navigate to Login on successful registration
    LaunchedEffect(isRegistrationSuccessful) {
        if (isRegistrationSuccessful) {
            onNavigateToLogin()
        }
    }

    PotyTheme(darkTheme = true, dynamicColor = false) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.onBackground,
        ) { innerPadding ->
            if (isLandscape) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {

                    var title: String? = null
                    if (windowSizeClass.isLandscape() && !windowSizeClass.isTablet()) {
                        title = when (state.currentStep) {
                            1, 2 -> "Registrarse"
                            3 ->  "¡Ya Casi!"
                            else -> null
                        }
                    }

                    LoginRegisterImageSection(
                        modifier = Modifier
                            .fillMaxHeight(),
                        windowSizeClass = windowSizeClass,
                        title = title
                    )

                    RegistrationContentSection(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        state = state,
                        viewModel = viewModel,
                        onNavigateToLogin = onNavigateToLogin,
                        contentPadding = contentPadding,
                        windowSizeClass = windowSizeClass
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LoginRegisterImageSection(
                        modifier = Modifier,
                        windowSizeClass = windowSizeClass
                    )

                    RegistrationContentSection(
                        modifier = Modifier
                            .fillMaxWidth(),
                        state = state,
                        viewModel = viewModel,
                        onNavigateToLogin = onNavigateToLogin,
                        contentPadding = contentPadding,
                        windowSizeClass = windowSizeClass
                    )
                }
            }
        }
    }
}


@Composable
fun LoginNavigationText(
    modifier: Modifier = Modifier,
    currentStep: Int,
    onBackClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    when {
        currentStep > 1 -> {
            // "Volver para atrás" button
            TextButton(
                onClick = onBackClick,
                modifier = modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Volver para atrás",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
        currentStep == 1 -> {
            // "Iniciar sesión" text with underline
            TextButton(
                onClick = onLoginClick,
                modifier = modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("¿Ya tenés una cuenta? ")
                        withStyle(
                            style = SpanStyle(
                                textDecoration = TextDecoration.Underline,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        ) {
                            append("Iniciar sesión")
                        }
                    },
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Composable
fun RegistrationContentSection(
    modifier: Modifier,
    state: RegistrationState,
    viewModel: RegistrationViewModel,
    onNavigateToLogin: () -> Unit,
    contentPadding: Dp,
    windowSizeClass: WindowSizeClass
) {
    Column(
        modifier = modifier
            .padding(contentPadding)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        // Registration steps logic remains the same
        when (state.currentStep) {
            1 -> StepOne(
                name = state.name,
                surname = state.surname,
                birthDate = state.birthday,
                onNameChange = { viewModel.onEvent(RegistrationEvent.UpdateName(it)) },
                onSurnameChange = { viewModel.onEvent(RegistrationEvent.UpdateSurname(it)) },
                onBirthdayChange = { viewModel.onEvent(RegistrationEvent.UpdateBirthday(it)) },
                onNext = { viewModel.onEvent(RegistrationEvent.NextStep) },
                errorMessage = state.errorMessage,
                windowSizeClass = windowSizeClass,
                onBackClick = { viewModel.onEvent(RegistrationEvent.PreviousStep) },
                onNavigateToLogin = onNavigateToLogin
            )

            2 -> StepTwo(
                email = state.email,
                password = state.password,
                onEmailChange = { viewModel.onEvent(RegistrationEvent.UpdateEmail(it)) },
                onPasswordChange = { viewModel.onEvent(RegistrationEvent.UpdatePassword(it)) },
                onNext = { viewModel.onEvent(RegistrationEvent.NextStep) },
                errorMessage = state.errorMessage,
                windowSizeClass = windowSizeClass,
                onBackClick = { viewModel.onEvent(RegistrationEvent.PreviousStep) },
                onNavigateToLogin = onNavigateToLogin
            )

            3 -> StepThree(
                confirmationCode = state.confirmationCode,
                email = state.email,
                onConfirmationCodeChange = {
                    viewModel.onEvent(RegistrationEvent.UpdateConfirmationCode(it))
                },
                onSubmit = { viewModel.onEvent(RegistrationEvent.Submit) },
                errorMessage = state.errorMessage,
                windowSizeClass = windowSizeClass,
                onBackClick = { viewModel.onEvent(RegistrationEvent.PreviousStep) },
                onNavigateToLogin = onNavigateToLogin
            )
        }

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

    }
}


@Composable
fun StepOne(
    name: String,
    surname: String,
    birthDate: String,
    onBirthdayChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onSurnameChange: (String) -> Unit,
    onNext: () -> Unit,
    errorMessage: String,
    windowSizeClass: WindowSizeClass,
    onBackClick: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = if (windowSizeClass.isTablet()) 32.dp else 16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(!windowSizeClass.isTablet() && !windowSizeClass.isLandscape()){
            RegistrationTitle(
                windowSizeClass = windowSizeClass,
                currentStep = 1
            )
        }

        TextFieldWithLabel(
            label = "Nombre",
            value = name,
            onValueChange = onNameChange,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldWithLabel(
            label = "Apellido",
            value = surname,
            onValueChange = onSurnameChange,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))


        val showCal =   if(!windowSizeClass.isTablet() && windowSizeClass.isLandscape()) false else true
        CompactDateFieldWithLabel(
            label = "Fecha de Nacimiento (DD/MM/AAAA)",
            value = birthDate,
            onValueChange = onBirthdayChange,
            showCal = showCal
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onNext() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Siguiente", color = MaterialTheme.colorScheme.onBackground)
        }
        if (errorMessage.isNotEmpty()) {
            ErrorMessage(message = errorMessage)
        }
        Spacer(modifier = Modifier.height(16.dp))
        LoginNavigationText(
            currentStep = 1,
            onBackClick = onBackClick,
            onLoginClick = onNavigateToLogin
        )
    }
}

@Composable
fun StepTwo(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onNext: () -> Unit,
    errorMessage: String,
    windowSizeClass: WindowSizeClass,
    onBackClick: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var localPasswordCheck by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(horizontal = if (windowSizeClass.isTablet()) 32.dp else 16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if(!windowSizeClass.isTablet() && !windowSizeClass.isLandscape()){
            RegistrationTitle(
                windowSizeClass = windowSizeClass,
                currentStep = 2
            )
        }
        TextFieldWithLabel(
            label = "Correo Electrónico",
            value = email,
            onValueChange = onEmailChange,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordFieldWithLabel(
            label = "Contraseña",
            value = password,
            onValueChange = onPasswordChange,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordFieldWithLabel(
            label = "Confirmar Contraseña",
            value = localPasswordCheck,
            onValueChange = { localPasswordCheck = it },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onNext() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Siguiente", color = MaterialTheme.colorScheme.onBackground)
        }

        if (errorMessage.isNotEmpty()) {
            ErrorMessage(message = errorMessage)
        }
        Spacer(modifier = Modifier.height(16.dp))
        LoginNavigationText(
            currentStep = 2,
            onBackClick = onBackClick,
            onLoginClick = onNavigateToLogin
        )
    }
}

@Composable
fun StepThree(
    confirmationCode: String,
    email: String,
    onConfirmationCodeChange: (String) -> Unit,
    onSubmit: () -> Unit,
    errorMessage: String,
    windowSizeClass: WindowSizeClass,
    onBackClick: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = if (windowSizeClass.isTablet()) 32.dp else 16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(!windowSizeClass.isTablet() && !windowSizeClass.isLandscape()){
            RegistrationTitle(
                windowSizeClass = windowSizeClass,
                currentStep = 3
            )
        }
        Text(
            text = "Para finalizar tu registro, ingresá el código que fue enviado a:",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = email,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        ThickTextFieldWithLabel(
            value = confirmationCode,
            onValueChange = onConfirmationCodeChange,
            isPassword = false,
            modifier = Modifier.fillMaxWidth()
        )

        if (errorMessage.isNotEmpty()) {
            ErrorMessage(message = errorMessage)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onSubmit() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Registrarse",
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        LoginNavigationText(
            currentStep = 3,
            onBackClick = onBackClick,
            onLoginClick = onNavigateToLogin
        )
    }
}

@Composable
fun RegistrationTitle(
    currentStep: Int,
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier
) {
    Text(
        text = if (currentStep < 3) "Registrarse" else "¡Ya Casi!",
        style = if (windowSizeClass.isTablet()) MaterialTheme.typography.titleLarge else if(!windowSizeClass.isTablet() && windowSizeClass.isLandscape() ) MaterialTheme.typography.titleSmall else MaterialTheme.typography.titleMedium ,
        modifier = modifier
            .padding(top = 20.dp, bottom = 10.dp)
    )
}


@Preview(
    name = "Medium Phone Portrait",
    device = "spec:width=411dp,height=914dp",
    showBackground = true
)
@Composable
fun RegistrationScreenMediumPhonePortraitPreview() {
    RegistrationScreen(
        onRegisterSuccess = {},
        onNavigateToLogin = {},
        mockWindowSizeClass = WindowSizeClass.MediumPhone
    )
}

@Preview(
    name = "Medium Phone Landscape",
    device = "spec:width=914dp,height=411dp",
    showBackground = true
)
@Composable
fun RegistrationScreenMediumPhoneLandscapePreview() {
    RegistrationScreen(
        onRegisterSuccess = {},
        onNavigateToLogin = {},
        mockWindowSizeClass = WindowSizeClass.MediumPhoneLandscape
    )
}

@Preview(
    name = "Medium Tablet Portrait",
    device = "spec:width=800dp,height=1280dp",
    showBackground = true
)
@Composable
fun RegistrationScreenMediumTabletPortraitPreview() {
    RegistrationScreen(
        onRegisterSuccess = {},
        onNavigateToLogin = {},
        mockWindowSizeClass = WindowSizeClass.MediumTablet
    )
}

@Preview(
    name = "Medium Tablet Landscape",
    device = "spec:width=1280dp,height=800dp",
    showBackground = true
)
@Composable
fun RegistrationScreenMediumTabletLandscapePreview() {
    RegistrationScreen(
        onRegisterSuccess = {},
        onNavigateToLogin = {},
        mockWindowSizeClass = WindowSizeClass.MediumTabletLandscape
    )
}



