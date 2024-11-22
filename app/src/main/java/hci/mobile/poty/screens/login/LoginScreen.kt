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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import hci.mobile.poty.MyApplication
import hci.mobile.poty.R
import hci.mobile.poty.ui.components.LoginRegisterImageSection
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
    viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = LoginViewModel.provideFactory(
        LocalContext.current.applicationContext as MyApplication
    )),
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
                    var title: String? = null
                    title = if (!windowSizeClass.isTablet()) stringResource(R.string.log_in) else null

                    LoginRegisterImageSection(
                        modifier = Modifier
                            .fillMaxHeight(),
                        windowSizeClass = windowSizeClass,
                        title = title
                    )
                }
                Column(modifier = Modifier
                    .padding(innerPadding)
                ){
                    LoginContentSection(
                        modifier = Modifier
                            .fillMaxHeight().fillMaxWidth(),
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
                        LoginRegisterImageSection(
                            modifier = Modifier
                                .fillMaxWidth(),
                            windowSizeClass = windowSizeClass
                        )
                    }else{
                        LoginRegisterImageSection(
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
        if(windowSizeClass.isTablet() || (!windowSizeClass.isTablet() && !windowSizeClass.isLandscape()) ){
            Text(
                text =  stringResource(R.string.log_in),
                style = if (windowSizeClass.isTablet()) MaterialTheme.typography.titleLarge else MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )

        }

        TextFieldWithLabel(
            label =  stringResource(R.string.email),
            value = state.email,
            onValueChange = { viewModel.updateEmail(it) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        PasswordFieldWithLabel(
            label =  stringResource(R.string.password),
            value = state.password,
            onValueChange = { viewModel.updatePassword(it) },
            modifier = Modifier.fillMaxWidth()
        )

        if(windowSizeClass != WindowSizeClass.MediumPhoneLandscape){
            Spacer(modifier = Modifier.height(16.dp))
        }else{
            Spacer(modifier = Modifier.height(8.dp))
        }

        if(windowSizeClass.isTablet() || !windowSizeClass.isTablet() && !windowSizeClass.isLandscape()){
            TextButton(
                onClick = onNavigateToRegister,
                modifier = Modifier.align(Alignment.Start)
            ) {
                Text(
                    text = stringResource(R.string.i_forgor_my_password),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }else{
            if (state.errorMessage.isNotEmpty()) {
                Text(
                    text = state.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }else{
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = onNavigateToRegister,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        Text(
                            text = stringResource(R.string.i_forgor_my_password),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                    TextButton(
                        onClick = onNavigateToRegister,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        Text(
                            text = stringResource(R.string.sign_up),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }

        if(windowSizeClass != WindowSizeClass.MediumPhoneLandscape){
            Spacer(modifier = Modifier.height(16.dp))
        }else{
            Spacer(modifier = Modifier.height(4.dp))
        }

        Button(
            onClick = {
                viewModel.validateAndLogin()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.log_in),
                color = White,
                style = MaterialTheme.typography.bodyLarge,
            )
        }

        if(WindowSizeClass.MediumPhoneLandscape != windowSizeClass){
            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = onNavigateToRegister,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.sign_up),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

        }

        if (state.errorMessage.isNotEmpty() && (windowSizeClass.isTablet() || !windowSizeClass.isTablet() && !windowSizeClass.isLandscape())) {
            Text(
                text = state.errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

