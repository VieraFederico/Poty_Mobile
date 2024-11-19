package hci.mobile.poty.screens.register
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hci.mobile.poty.R
import hci.mobile.poty.ui.theme.PotyTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import hci.mobile.poty.utils.ErrorMessage
import hci.mobile.poty.utils.TextFieldWithLabel
import androidx.lifecycle.viewmodel.compose.viewModel
import hci.mobile.poty.utils.CompactDateFieldWithLabel
import hci.mobile.poty.utils.ThickTextFieldWithLabel

@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel = viewModel(),
    onRegisterSuccess: () -> Unit, // Esto puede llevar al Dashboard
    onNavigateToLogin: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val isRegistrationSuccessful by viewModel.isRegistrationSuccessful.collectAsState()

    // Si el registro es exitoso, navega al Dashboard o Login
    LaunchedEffect(isRegistrationSuccessful) {
        if (isRegistrationSuccessful) {
            onRegisterSuccess()
        }
    }

    PotyTheme(darkTheme = true, dynamicColor = false) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.onBackground,
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Top
            ) {
                Image(
                    painter = painterResource(id = R.drawable.loginreg),
                    contentDescription = "Foto Landing",
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = if (state.currentStep < 3) "Registrarse" else "¡Ya Casi!",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(top = 20.dp, bottom = 10.dp)
                        .align(Alignment.CenterHorizontally)
                )

                when (state.currentStep) {
                    1 -> StepOne(
                        name = state.name,
                        surname = state.surname,
                        birthDate = state.birthday,
                        onNameChange = { viewModel.onEvent(RegistrationEvent.UpdateName(it)) },
                        onSurnameChange = { viewModel.onEvent(RegistrationEvent.UpdateSurname(it)) },
                        onBirthdayChange = { viewModel.onEvent(RegistrationEvent.UpdateBirthday(it)) },
                        onNext = { viewModel.onEvent(RegistrationEvent.NextStep) },
                        errorMessage = state.errorMessage
                    )
                    2 -> StepTwo(
                        email = state.email,
                        password = state.password,
                        onEmailChange = { viewModel.onEvent(RegistrationEvent.UpdateEmail(it)) },
                        onPasswordChange = { viewModel.onEvent(RegistrationEvent.UpdatePassword(it)) },
                        onNext = { viewModel.onEvent(RegistrationEvent.NextStep) },
                        errorMessage = state.errorMessage
                    )
                    3 -> StepThree(
                        confirmationCode = state.confirmationCode,
                        email = state.email,
                        onConfirmationCodeChange = { viewModel.onEvent(RegistrationEvent.UpdateConfirmationCode(it)) },
                        onSubmit = { viewModel.onEvent(RegistrationEvent.Submit) },
                        errorMessage = state.errorMessage
                    )
                }

                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                if (state.currentStep > 1) {
                    TextButton(
                        onClick = { viewModel.onEvent(RegistrationEvent.PreviousStep) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = "Volver para atrás",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }
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
    errorMessage: String
) {
    var localName by remember { mutableStateOf(name) }
    var localSurname by remember { mutableStateOf(surname) }
    var localBirthDate by remember { mutableStateOf(birthDate) }

    Column(modifier = Modifier.padding(15.dp)) {

        TextFieldWithLabel(
            label = "Nombre",
            value = localName,
            onValueChange = {
                localName = it
                onNameChange(it)
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldWithLabel(
            label = "Apellido",
            value = localSurname,
            onValueChange = {
                localSurname = it
                onSurnameChange(it)
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        CompactDateFieldWithLabel(
            label = "Fecha de Nacimiento (DD/MM/AAAA)",
            value = localBirthDate,
            onValueChange = {
                localBirthDate = it
                onBirthdayChange(it)
            }
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
    }
}

@Composable
fun StepTwo(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onNext: () -> Unit,
    errorMessage: String
) {
    var localEmail by remember { mutableStateOf(email) }
    var localPassword by remember { mutableStateOf(password) }
    var localPasswordCheck by remember { mutableStateOf(password) }

    Column(modifier = Modifier.padding(15.dp)) {
        TextFieldWithLabel(
            label = "Correo Electrónico",
            value = localEmail,
            onValueChange = {
                localEmail = it
                onEmailChange(it)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextFieldWithLabel(
            label = "Contraseña",
            value = localPassword,
            onValueChange = {
                localPassword = it
                onPasswordChange(it)
            },
            isPassword = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextFieldWithLabel(
            label = "Confirmar Contraseña",
            value = localPasswordCheck,
            onValueChange = {
                localPasswordCheck = it
            },
            isPassword = true
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
    }
}




@Composable
fun StepThree(
    confirmationCode: String,
    email: String,
    onConfirmationCodeChange: (String) -> Unit,
    onSubmit: () -> Unit,
    errorMessage: String
) {
    var localConfirmationCode by remember { mutableStateOf(confirmationCode) }

    Column(
        modifier = Modifier.padding(15.dp)
    ) {
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
            value = localConfirmationCode,
            onValueChange = { localConfirmationCode = it },
            isPassword = false
        )

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onConfirmationCodeChange(localConfirmationCode)
                onSubmit()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Registrarse",
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}



@Preview
@Composable
fun RegisterScreenPreview() {
    RegistrationScreen(
        onRegisterSuccess = {},
        onNavigateToLogin = {}
    )
}
