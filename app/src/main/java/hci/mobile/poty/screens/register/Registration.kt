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


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.platform.LocalContext

import hci.mobile.poty.MyApplication
import hci.mobile.poty.utils.ErrorMessage
import hci.mobile.poty.utils.TextFieldWithLabel

import hci.mobile.poty.utils.CompactDateFieldWithLabel
import hci.mobile.poty.utils.ThickTextFieldWithLabel



@Composable
fun RegistrationScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    mockWindowSizeClass: WindowSizeClass? = null
) {
    val viewModel: RegistrationViewModel = viewModel(factory = RegistrationViewModel.provideFactory(
        LocalContext.current.applicationContext as MyApplication
        )
    )

    val state = viewModel.state
    val isRegistrationSuccessful = viewModel.isRegistrationSuccessful

    val windowSizeClass = mockWindowSizeClass ?: calculateWindowSizeClass()
    val isLandscape = windowSizeClass.isLandscape()
    val contentPadding = if (windowSizeClass.isTablet()) 32.dp else if(!windowSizeClass.isLandscape()) 16.dp else 8.dp

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



