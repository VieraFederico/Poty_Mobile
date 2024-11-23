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
import androidx.compose.ui.res.stringResource

import hci.mobile.poty.MyApplication
import hci.mobile.poty.R
import hci.mobile.poty.utils.ErrorMessage
import hci.mobile.poty.utils.TextFieldWithLabel

import hci.mobile.poty.utils.CompactDateFieldWithLabel
import hci.mobile.poty.utils.ThickTextFieldWithLabel



@Composable
fun RegistrationScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,

) {

    val state: RegistrationState
    var isRegistrationSuccessful: Boolean
    val onEvent: (RegistrationEvent) -> Unit

    val viewModel: RegistrationViewModel = viewModel(factory = RegistrationViewModel.provideFactory(LocalContext.current.applicationContext as MyApplication))
    state = viewModel.state
    isRegistrationSuccessful = viewModel.isRegistrationSuccessful
    onEvent = { viewModel.onEvent(it) }

    val windowSizeClass = calculateWindowSizeClass()
    val isLandscape = windowSizeClass.isLandscape()
    val contentPadding = if (windowSizeClass.isTablet()) 32.dp else if(!windowSizeClass.isLandscape()) 16.dp else 8.dp

    LaunchedEffect(isRegistrationSuccessful) {
        if (isRegistrationSuccessful) {
            onNavigateToLogin()
            isRegistrationSuccessful = false
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
                            1, 2 -> stringResource(R.string.sign_up)
                            3 ->  stringResource(R.string.almost_there)
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
                        onEvent = onEvent,
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
                        onEvent = onEvent,
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
                    text = stringResource(R.string.go_back),
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
                        append(stringResource(R.string.already_have_an_account))
                        append(" ")
                        withStyle(
                            style = SpanStyle(
                                textDecoration = TextDecoration.Underline,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        ) {
                            append(stringResource(R.string.log_in))
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
    onEvent: (RegistrationEvent) -> Unit,
    onNavigateToLogin: () -> Unit,
    contentPadding: Dp,
    windowSizeClass: WindowSizeClass
) {
    val isLandscapeAndNotTablet = windowSizeClass.isLandscape() && !windowSizeClass.isTablet()

    Column(
        modifier = modifier
            .padding(contentPadding)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLandscapeAndNotTablet) {
            when (state.currentStep) {
                1 -> when (state.currentSubStep) {
                    0 -> SubStepOnePartOne(
                        name = state.name,
                        onNameChange = { onEvent(RegistrationEvent.UpdateName(it)) },
                        onNext = { onEvent(RegistrationEvent.NextSubStep) },
                        errorMessage = state.errorMessage,
                        windowSizeClass = windowSizeClass,
                        onBackClick = { onEvent(RegistrationEvent.PreviousStep) },
                        onNavigateToLogin = onNavigateToLogin,
                        surname = state.surname,
                        onSurnameChange = { onEvent(RegistrationEvent.UpdateSurname(it)) }
                    )
                    1 -> SubStepOnePartTwo(
                        birthDate = state.birthday,
                        onBirthdayChange = { onEvent(RegistrationEvent.UpdateBirthday(it)) },
                        onNext = { onEvent(RegistrationEvent.NextSubStep) },
                        onBackClick = { onEvent(RegistrationEvent.PreviousSubStep) },
                        errorMessage = state.errorMessage,
                        email = state.email,
                        onEmailChange =  { onEvent(RegistrationEvent.UpdateEmail(it)) },
                        )
                }
                2 ->  SubStepTwo(
                        onNext = { onEvent(RegistrationEvent.NextSubStep) },
                        onBackClick = { onEvent(RegistrationEvent.PreviousSubStep) },
                        password = state.password,
                        onPasswordChange = { onEvent(RegistrationEvent.UpdatePassword(it)) },
                        errorMessage = state.errorMessage,
                    )


                3 -> StepThree(
                    confirmationCode = state.confirmationCode,
                    email = state.email,
                    onConfirmationCodeChange = {
                        onEvent(RegistrationEvent.UpdateConfirmationCode(it))
                    },
                    onSubmit = { onEvent(RegistrationEvent.verifyCode) },
                    errorMessage = state.errorMessage,
                    windowSizeClass = windowSizeClass,
                    onBackClick = { onEvent(RegistrationEvent.PreviousStep) },
                    onNavigateToLogin = onNavigateToLogin,
                    state = state
                )
            }
        } else {
            when (state.currentStep) {
                1 -> StepOne(
                    name = state.name,
                    surname = state.surname,
                    birthDate = state.birthday,
                    onNameChange = { onEvent(RegistrationEvent.UpdateName(it)) },
                    onSurnameChange = { onEvent(RegistrationEvent.UpdateSurname(it)) },
                    onBirthdayChange = { onEvent(RegistrationEvent.UpdateBirthday(it)) },
                    onNext = { onEvent(RegistrationEvent.NextStep) },
                    errorMessage = state.errorMessage,
                    windowSizeClass = windowSizeClass,
                    onBackClick = { onEvent(RegistrationEvent.PreviousStep) },
                    onNavigateToLogin = onNavigateToLogin
                )
                2 -> StepTwo(
                    email = state.email,
                    password = state.password,
                    onEmailChange = { onEvent(RegistrationEvent.UpdateEmail(it)) },
                    onPasswordChange = { onEvent(RegistrationEvent.UpdatePassword(it)) },
                    onNext = { onEvent(RegistrationEvent.NextStep) },
                    errorMessage = state.errorMessage,
                    windowSizeClass = windowSizeClass,
                    onBackClick = { onEvent(RegistrationEvent.PreviousStep) },
                    onNavigateToLogin = onNavigateToLogin
                )
                3 -> StepThree(
                    confirmationCode = state.confirmationCode,
                    email = state.email,
                    onConfirmationCodeChange = {
                        onEvent(RegistrationEvent.UpdateConfirmationCode(it))
                    },
                    onSubmit = { onEvent(RegistrationEvent.verifyCode) },
                    errorMessage = state.errorMessage,
                    windowSizeClass = windowSizeClass,
                    onBackClick = { onEvent(RegistrationEvent.PreviousStep) },
                    onNavigateToLogin = onNavigateToLogin,
                    state = state
                )
            }
        }


    }
}

@Composable
fun SubStepOnePartOne(
    name: String,
    onNameChange: (String) -> Unit,
    onNext: () -> Unit,
    errorMessage: String,
    windowSizeClass: WindowSizeClass,
    onBackClick: () -> Unit,
    onNavigateToLogin: () -> Unit,
    surname: String,
    onSurnameChange: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextFieldWithLabel(
            label = stringResource(R.string.name),
            value = name,
            onValueChange = onNameChange,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextFieldWithLabel(
            label = stringResource(R.string.surname),
            value = surname,
            onValueChange = onSurnameChange,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onNext() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = stringResource(R.string.next), color = MaterialTheme.colorScheme.onBackground)
        }

        Spacer(modifier = Modifier.height(16.dp))

        LoginNavigationText(
            currentStep = 1,
            onBackClick = onBackClick,
            onLoginClick = onNavigateToLogin
        )
        Spacer(modifier = Modifier.height(4.dp))
        if (errorMessage.isNotEmpty()) {
            ErrorMessage(message = errorMessage)
        }

    }
}

@Composable
fun SubStepOnePartTwo(
    birthDate: String,
    onBirthdayChange: (String) -> Unit,
    onNext: () -> Unit,
    onBackClick: () -> Unit,
    errorMessage: String,
    onEmailChange: (String) -> Unit,
    email: String
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CompactDateFieldWithLabel(
            label = stringResource(R.string.date_of_birth),
            value = birthDate,
            onValueChange = onBirthdayChange,
            showCal = false
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextFieldWithLabel(
            label = stringResource(R.string.email),
            value = email,
            onValueChange = onEmailChange,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onNext() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = stringResource(R.string.next), color = MaterialTheme.colorScheme.onBackground)
        }
        Spacer(modifier = Modifier.height(16.dp))
        LoginNavigationText(
            currentStep = 2,
            onLoginClick = {},
            onBackClick = onBackClick
        )
        Spacer(modifier = Modifier.height(4.dp))
        ErrorMessage(message = errorMessage)
    
    }
}

@Composable
fun SubStepTwo(
    password: String,
    onPasswordChange: (String) -> Unit,
    onNext: () -> Unit,
    onBackClick: () -> Unit,
    errorMessage: String,
) {
    var localPasswordCheck by remember { mutableStateOf("") }
    var passwordMismatchError by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PasswordFieldWithLabel(
            label = stringResource(R.string.password),
            value = password,
            onValueChange = {
                onPasswordChange(it)
                passwordMismatchError = false
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        PasswordFieldWithLabel(
            label = stringResource(R.string.confirm_password),
            value = localPasswordCheck,
            onValueChange = {
                localPasswordCheck = it
                passwordMismatchError = false
            },
            modifier = Modifier.fillMaxWidth()
        )



        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (password == localPasswordCheck) {
                    onNext()
                } else {
                    passwordMismatchError = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = stringResource(R.string.next), color = MaterialTheme.colorScheme.onBackground)
        }

        Spacer(modifier = Modifier.height(8.dp))

        LoginNavigationText(
            currentStep = 2,
            onBackClick = onBackClick,
            onLoginClick = { }
        )

        Spacer(modifier = Modifier.height(4.dp))

        if (errorMessage.isNotEmpty()) {
            ErrorMessage(message = errorMessage)
        }else if (passwordMismatchError) {
            ErrorMessage(message = stringResource(R.string.passwords_dont_match))

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
            label = stringResource(R.string.name),
            value = name,
            onValueChange = onNameChange,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldWithLabel(
            label = stringResource(R.string.surname),
            value = surname,
            onValueChange = onSurnameChange,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))


        val showCal =   if(!windowSizeClass.isTablet() && windowSizeClass.isLandscape()) false else true
        CompactDateFieldWithLabel(
            label = stringResource(R.string.date_of_birth),
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
            Text(text = stringResource(R.string.next), color = MaterialTheme.colorScheme.onBackground)
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (errorMessage.isNotEmpty()) {
            ErrorMessage(message = errorMessage)
        }else{
            LoginNavigationText(
                currentStep = 1,
                onBackClick = onBackClick,
                onLoginClick = onNavigateToLogin
            )
        }


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
    var passwordMismatchError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(horizontal = if (windowSizeClass.isTablet()) 32.dp else 16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (!windowSizeClass.isTablet() && !windowSizeClass.isLandscape()) {
            RegistrationTitle(
                windowSizeClass = windowSizeClass,
                currentStep = 2
            )
        }

        TextFieldWithLabel(
            label = stringResource(R.string.email),
            value = email,
            onValueChange = onEmailChange,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordFieldWithLabel(
            label = stringResource(R.string.password),
            value = password,
            onValueChange = {
                onPasswordChange(it)
                passwordMismatchError = false // Reset mismatch error on input
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordFieldWithLabel(
            label = stringResource(R.string.confirm_password),
            value = localPasswordCheck,
            onValueChange = {
                localPasswordCheck = it
                passwordMismatchError = false // Reset mismatch error on input
            },
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (password == localPasswordCheck) {
                    onNext()
                } else {
                    passwordMismatchError = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = stringResource(R.string.next), color = MaterialTheme.colorScheme.onBackground)
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (errorMessage.isNotEmpty()) {
            ErrorMessage(message = errorMessage)
        } else if (passwordMismatchError)  {
            ErrorMessage(message = stringResource(R.string.passwords_dont_match))
        }else {
            LoginNavigationText(
                currentStep = 2,
                onBackClick = onBackClick,
                onLoginClick = onNavigateToLogin
            )
        }
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
    onNavigateToLogin: () -> Unit,
    state: RegistrationState
) {
    val horizontalPadding = when {
        windowSizeClass.isTablet() -> 32.dp
        windowSizeClass == WindowSizeClass.MediumPhoneLandscape -> 8.dp
        else -> 16.dp
    }

    val spacing = if (windowSizeClass == WindowSizeClass.MediumPhoneLandscape) 8.dp else 16.dp

    Column(
        modifier = Modifier
            .padding(horizontal = horizontalPadding)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!windowSizeClass.isTablet() && !windowSizeClass.isLandscape()) {
            RegistrationTitle(
                windowSizeClass = windowSizeClass,
                currentStep = 3
            )
        }
        Text(
            text = stringResource(R.string.to_complete_your_registration),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = email,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(spacing))

        if (windowSizeClass == WindowSizeClass.MediumPhoneLandscape) {
            TextFieldWithLabel(
                label = "",
                value = confirmationCode,
                onValueChange = onConfirmationCodeChange,
                isPassword = false,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            ThickTextFieldWithLabel(
                value = confirmationCode,
                onValueChange = onConfirmationCodeChange,
                isPassword = false,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(spacing))

        Button(
            onClick = { onSubmit() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.sign_up),
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.height(spacing))

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            LoginNavigationText(
                currentStep = 3,
                onBackClick = onBackClick,
                onLoginClick = onNavigateToLogin
            )
            Spacer(modifier = Modifier.height(spacing))
            if (errorMessage.isNotEmpty()) {
                ErrorMessage(message = errorMessage)
            }
        }
    }
}


@Composable
fun RegistrationTitle(
    currentStep: Int,
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier
) {
    Text(
        text = if (currentStep < 3) stringResource(R.string.sign_up) else stringResource(R.string.almost_there),
        style = if (windowSizeClass.isTablet()) MaterialTheme.typography.titleLarge else if(!windowSizeClass.isTablet() && windowSizeClass.isLandscape() ) MaterialTheme.typography.titleSmall else MaterialTheme.typography.titleMedium ,
        modifier = modifier
            .padding(top = 20.dp, bottom = 10.dp)
    )
}


