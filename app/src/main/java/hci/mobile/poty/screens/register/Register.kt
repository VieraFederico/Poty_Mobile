/*package hci.mobile.poty.screens.landing

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hci.mobile.poty.R
import hci.mobile.poty.ui.theme.PotyTheme
import hci.mobile.poty.ui.theme.titleLargeItalic
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.key.Key.Companion.Calendar
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.Visibility
import hci.mobile.poty.screens.register.RegistrationEvent
import hci.mobile.poty.screens.register.RegistrationViewModel
import hci.mobile.poty.utils.CompactDateFieldWithLabel
import hci.mobile.poty.utils.ErrorMessage
import hci.mobile.poty.utils.TextFieldWithLabel
import java.util.Date

//@TODO: Modularizar
@Composable
fun RegisterScreen(
    viewModel: RegistrationViewModel
) {
    val state by viewModel.state.collectAsState()

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
                        .padding(top = 20.dp, bottom = 32.dp)
                        .align(Alignment.CenterHorizontally)
                )

                when (state.currentStep) {
                    1 -> StepOne(
                        name = state.name,
                        surname = state.surname,
                        birthDate = state.birthday,
                        gender = state.gender,
                        country = state.country,
                        city = state.city,
                        onNameChange = { viewModel.onEvent(RegistrationEvent.UpdateName(it)) },
                        onSurnameChange = { viewModel.onEvent(RegistrationEvent.UpdateSurname(it)) },
                        onBirthdayChange = { viewModel.onEvent(RegistrationEvent.UpdateBirthday(it)) },
                        onGenderChange = { viewModel.onEvent(RegistrationEvent.UpdateGender(it)) },
                        onCountryChange = { viewModel.onEvent(RegistrationEvent.UpdateCountry(it)) },
                        onCityChange = { viewModel.onEvent(RegistrationEvent.UpdateCity(it)) },
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

                }

                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

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
}/*
@Composable
fun RegisterScreen() {
    var currentStep by remember { mutableStateOf(1) }
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmationCode by remember {mutableStateOf("")}
    var birthDate by remember {mutableStateOf("")}

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
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                if(currentStep<3) {
                    Text(
                        text = "Registrarse",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .padding(top = 20.dp, bottom = 32.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
                else{
                    Text(
                        text = "¡Ya Casi!",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .padding(top = 20.dp, bottom = 32.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }

                when (currentStep) {
                    1 -> StepOne(name, surname) { newName, newSurname, newBirthDate ->
                        name = newName
                        surname = newSurname
                        birthDate = newBirthDate
                        currentStep = 2
                    }
                    2 ->  StepTwo(email,password){ newEmail, newPassword->
                        email= newEmail
                        password=newPassword
                        currentStep = 3
                    }
                    3 -> StepThree(confirmationCode,email){ newCode,email ->
                        confirmationCode = newCode
                    }
                }
                TextButton(
                    onClick = {/*Volver atras*/ },
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
}*/

@Composable
fun StepThree(confirmationCode: String, email:String, onConfirm: (String,String) -> Unit) {
    var localconfirmationCode by remember { mutableStateOf(confirmationCode) }
    var errorMessage by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.padding(15.dp)
    ) {
        Text(
            text="Para finalizar tu registro, ingresá el código que fue enviado a:",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text="$email",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        ThickTextFieldWithLabel(value = confirmationCode, onValueChange = { localconfirmationCode = it })
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {onConfirm(localconfirmationCode,email)}
        ) {
            Text(
            text = "Registrarse",
            color = MaterialTheme.colorScheme.onBackground
        )
        }
    }
}
/*
@Composable
fun StepTwo(email: String, password: String, onNext: (String, String) -> Unit) {
    var localEmail by remember { mutableStateOf(email) }
    var localPassword by remember { mutableStateOf(password) }
    var localPasswordCheck by remember { mutableStateOf(password) }
    var errorMessage by remember { mutableStateOf("") } // For error messages

    Column(
        modifier = Modifier.padding(15.dp)
    ) {
        TextFieldWithLabel(label = "Correo Electronico", value = localEmail, onValueChange = { localEmail = it })
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldWithLabel(label = "Contraseña", value = localPassword, onValueChange = { localPassword = it }, isPassword = true)
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldWithLabel(label = "Confirmar Contraseña", value = localPasswordCheck, onValueChange = { localPasswordCheck = it }, isPassword = true)
        Spacer(modifier = Modifier.height(50.dp))


        Button(
            onClick = {
                if (localPassword != localPasswordCheck) {
                    errorMessage = "Las contraseñas no coinciden."
                }
                if (localEmail.isEmpty()){
                    errorMessage = "Por favor, ingrese un correo electronico valido."
                }
                else if(localPassword.isEmpty()){
                    errorMessage = "Por favor, ingrese una contraseña valida."
                }
                else{
                    onNext(localEmail, localPassword)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
        ) {
            Text(
                text = "Siguiente",
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        ErrorMessage(message = errorMessage)

    }
}
*/
@Composable
fun StepOne(
    name: String,
    surname: String,
    birthDate: String,
    gender: String,
    country: String,
    city: String,
    onNameChange: (String) -> Unit,
    onSurnameChange: (String) -> Unit,
    onBirthdayChange: (String) -> Unit,
    onGenderChange: (String) -> Unit,
    onCountryChange: (String) -> Unit,
    onCityChange: (String) -> Unit,
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

        TextFieldWithLabel(
            label = "Fecha de nacimiento",
            value = localBirthDate,
            onValueChange = {
                localBirthDate = it
                onBirthdayChange(it)
            }
        )
        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = { onNext() },
            modifier = Modifier.fillMaxWidth().height(50.dp)
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

        // Asegúrate de tener campos para Gender, Country, y City
        // (Puedes agregar los campos de `gender`, `country` y `city` aquí si es necesario)

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = {
                if (localPassword != localPasswordCheck) {
                    //errorMessage = "Las contraseñas no coinciden."
                } else if (localEmail.isEmpty() || localPassword.isEmpty()) {
                    //errorMessage = "Por favor, ingrese un correo electrónico y una contraseña válidos."
                } else {
                    onNext()
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text(text = "Siguiente", color = MaterialTheme.colorScheme.onBackground)
        }

        if (errorMessage.isNotEmpty()) {
            ErrorMessage(message = errorMessage)
        }
    }
}

/*@Composable
fun StepOne(name: String, surname: String, onNext: (String, String, String) -> Unit) {
    var localName by remember { mutableStateOf(name) }
    var localSurname by remember { mutableStateOf(surname) }
    var errorMessage by remember { mutableStateOf("") } // For error messages
    var localBirthDate by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(15.dp)
    ) {
        TextFieldWithLabel(label = "Nombre", value = localName, onValueChange = { localName = it })
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldWithLabel(label = "Apellido", value = localSurname, onValueChange = { localSurname = it })
        Spacer(modifier = Modifier.height(16.dp))
        CompactDateFieldWithLabel(label = "Fecha de Nacimiento", value = localBirthDate, onValueChange = { localBirthDate = it })
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if(localName.isEmpty()){
                    errorMessage = "Por favor, ingrese un nombre valido."
                }
                else if(localSurname.isEmpty()){
                    errorMessage = "Por favor, ingrese un apellido valido."
                }
                else if(localBirthDate.isEmpty()){
                    errorMessage = "Por favor, ingrese una fecha de nacimiento valida."
                }
                else {
                    onNext(localName, localSurname, localBirthDate)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
        ) {
            Text(
                text = "Siguiente",
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        ErrorMessage(message = errorMessage)

    }
}*/
@Composable
fun ThickTextFieldWithLabel(value: String, onValueChange: (String) -> Unit, isPassword: Boolean = false) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(50.dp))
            .height(90.dp)
            .border(
                BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                RoundedCornerShape(50.dp)
            ),
        singleLine = true,
        textStyle = LocalTextStyle.current.copy(color = Color.Black),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
    )
}

*/
