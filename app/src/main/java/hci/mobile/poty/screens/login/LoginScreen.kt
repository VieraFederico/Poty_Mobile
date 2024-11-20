package hci.mobile.poty.screens.login

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hci.mobile.poty.ui.theme.PotyTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.Typography
import androidx.compose.runtime.*

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import hci.mobile.poty.R
import hci.mobile.poty.ui.theme.White
import hci.mobile.poty.utils.PasswordFieldWithLabel
import hci.mobile.poty.utils.TextFieldWithLabel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
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

                    Spacer(modifier = Modifier.height(30.dp))

                    Text(
                        text = "Inicio de Sesión",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .align(Alignment.CenterHorizontally)
                        )

                        Column(modifier = Modifier.padding(15.dp)) {

                        TextFieldWithLabel(
                            label = "Correo Electronico",
                            value = state.email,
                            onValueChange = {viewModel.updateEmail(it)}
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        PasswordFieldWithLabel(
                            label = "Contraseña",
                            value = state.password,
                            onValueChange = {viewModel.updatePassword(it)}
                        )

                            TextButton(
                                onClick = onNavigateToRegister,
                            ) {
                                Text(
                                    text="Olvidé mi Contraseña",
                                    style = MaterialTheme.typography.bodyLarge,
                                )
                            }

                        Spacer(modifier = Modifier.height(70.dp))

                        Button(
                            onClick = {
                                viewModel.validateAndLogin()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text="Iniciar Sesión",
                                color = White,
                                style = MaterialTheme.typography.bodyLarge,
                                )

                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        TextButton(
                            onClick = onNavigateToRegister,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text="Registrarse",
                                style = MaterialTheme.typography.bodyMedium,
                                )
                        }

                        Text(
                            text = state.errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
            }
        }
    }
}


@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        onLoginSuccess = {},
        onNavigateToRegister = {}
    )
}
