package hci.mobile.poty.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import hci.mobile.poty.R
import hci.mobile.poty.ui.theme.PotyTheme
import hci.mobile.poty.ui.theme.White
import hci.mobile.poty.utils.PasswordFieldWithLabel
import hci.mobile.poty.utils.TextFieldWithLabel
import hci.mobile.poty.utils.WindowSizeClass
import hci.mobile.poty.utils.calculateWindowSizeClass
import hci.mobile.poty.utils.isLandscape
import hci.mobile.poty.utils.isTablet

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
    mockWindowSizeClass: WindowSizeClass? = null
) {
    val state by viewModel.state.collectAsState()
    val windowSizeClass = mockWindowSizeClass ?: calculateWindowSizeClass()
    val isLandscape = windowSizeClass.isLandscape()
    val isTablet = windowSizeClass.isTablet()
    val contentPadding = if (isTablet) 32.dp else 16.dp

    viewModel.onLoginSuccess = onLoginSuccess

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
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                ) {
                    LoginImageSection(
                        modifier = Modifier
                            .fillMaxHeight(),
                        windowSizeClass = windowSizeClass
                    )
                }
                Column(modifier = Modifier
                    .padding(innerPadding)
                ){
                    LoginContentSection(
                        modifier = Modifier
                            .fillMaxHeight(),
                        state = state,
                        viewModel = viewModel,
                        onNavigateToRegister = onNavigateToRegister,
                        contentPadding = contentPadding,
                        windowSizeClass = windowSizeClass
                    )
                }
                }

            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    if((windowSizeClass.isTablet() && windowSizeClass.isLandscape()) || !windowSizeClass.isTablet()){
                        LoginImageSection(
                            modifier = Modifier
                                .fillMaxWidth(),
                            windowSizeClass = windowSizeClass
                        )
                    }else{
                        LoginImageSection(
                            modifier = Modifier
                                .fillMaxWidth().fillMaxHeight(0.5f),
                            windowSizeClass = windowSizeClass
                        )
                    }


                    LoginContentSection(
                        modifier = Modifier
                            .fillMaxWidth(),
                        state = state,
                        viewModel = viewModel,
                        onNavigateToRegister = onNavigateToRegister,
                        contentPadding = contentPadding,
                        windowSizeClass = windowSizeClass
                    )
                }
            }
        }
    }
}

@Composable
fun LoginImageSection(
    modifier: Modifier,
    windowSizeClass: WindowSizeClass
) {
    val isLandscape = windowSizeClass.isLandscape()
    val shape = if (isLandscape) {
        RoundedCornerShape(topStart = 0.dp, topEnd = 16.dp, bottomStart = 0.dp, bottomEnd = 16.dp)
    } else {
        RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 16.dp, bottomEnd = 16.dp)
    }

    Box(
        contentAlignment = Alignment.Center
    ) {
        if (!isLandscape) {
            Image(
                painter = painterResource(id = R.drawable.loginreg),
                contentDescription = "Login Image",
                modifier = modifier
                    .clip(shape)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        } else {
            val height = if(windowSizeClass.isTablet()) 0.8f else 1f
            Image(
                painter = painterResource(id = R.drawable.login2),
                contentDescription = "Login Image",
                modifier = modifier.wrapContentSize(Alignment.Center).fillMaxWidth(0.5f).fillMaxHeight(height),
                contentScale = ContentScale.Crop
            )}

    }

}


@Composable
fun LoginContentSection(
    modifier: Modifier,
    state: LoginState,
    viewModel: LoginViewModel,
    onNavigateToRegister: () -> Unit,
    contentPadding: Dp,
    windowSizeClass: WindowSizeClass
) {
    Column(
        modifier = modifier
            .padding(contentPadding)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Inicio de Sesión",
            style = if (windowSizeClass.isTablet()) MaterialTheme.typography.titleLarge else MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(bottom = 16.dp)
        )

        TextFieldWithLabel(
            label = "Correo Electrónico",
            value = state.email,
            onValueChange = { viewModel.updateEmail(it) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        PasswordFieldWithLabel(
            label = "Contraseña",
            value = state.password,
            onValueChange = { viewModel.updatePassword(it) },
            modifier = Modifier.fillMaxWidth()
        )

        TextButton(
            onClick = onNavigateToRegister,
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text(
                text = "Olvidé mi Contraseña",
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.validateAndLogin()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Iniciar Sesión",
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
                text = "Registrarse",
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        if (state.errorMessage.isNotEmpty()) {
            Text(
                text = state.errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}



@Preview(
    name = "Medium Phone Portrait",
    device = "spec:width=411dp,height=914dp",
    showBackground = true
)
@Composable
fun LoginScreenMediumPhonePortraitPreview() {
    LoginScreen(
        onLoginSuccess = {},
        onNavigateToRegister = {},
        mockWindowSizeClass = WindowSizeClass.MediumPhone
    )
}

@Preview(
    name = "Medium Phone Landscape",
    device = "spec:width=914dp,height=411dp",
    showBackground = true
)
@Composable
fun LoginScreenMediumPhoneLandscapePreview() {
    LoginScreen(
        onLoginSuccess = {},
        onNavigateToRegister = {},
        mockWindowSizeClass = WindowSizeClass.MediumPhoneLandscape
    )
}

@Preview(
    name = "Medium Tablet Portrait",
    device = "spec:width=800dp,height=1280dp",
    showBackground = true
)
@Composable
fun LoginScreenMediumTabletPortraitPreview() {
    LoginScreen(
        onLoginSuccess = {},
        onNavigateToRegister = {},
        mockWindowSizeClass = WindowSizeClass.MediumTablet
    )
}

@Preview(
    name = "Medium Tablet Landscape",
    device = "spec:width=1280dp,height=800dp",
    showBackground = true
)
@Composable
fun LoginScreenMediumTabletLandscapePreview() {
    LoginScreen(
        onLoginSuccess = {},
        onNavigateToRegister = {},
        mockWindowSizeClass = WindowSizeClass.MediumTabletLandscape
    )
}
