package hci.mobile.poty.screens.charge

import ChargeScreenState
import ChargeScreenViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hci.mobile.poty.ui.theme.PotyTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import hci.mobile.poty.R
import hci.mobile.poty.ui.components.BackButton
import hci.mobile.poty.ui.theme.White
import hci.mobile.poty.ui.theme.titleSmallSemiBold
import hci.mobile.poty.utils.ErrorMessage
import hci.mobile.poty.utils.NumberFieldWithLabel
import hci.mobile.poty.utils.WindowSizeClass
import hci.mobile.poty.utils.calculateWindowSizeClass
import hci.mobile.poty.ui.components.ResponsiveNavBar
import hci.mobile.poty.utils.ReadOnlyTextFieldWithLabel
import hci.mobile.poty.utils.isLandscape
import hci.mobile.poty.utils.isTablet
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import hci.mobile.poty.MyApplication

@Composable
fun ChargeScreen(
    viewModel: ChargeScreenViewModel = viewModel(factory = ChargeScreenViewModel.provideFactory(
        LocalContext.current.applicationContext as MyApplication,
        context = LocalContext.current
    )),
    onNavigateBack: () -> Unit = {},

) {
    val state by viewModel.state.collectAsState()
    val windowSizeClass = calculateWindowSizeClass()

    val isLandscape = windowSizeClass.isLandscape()
    val contentPadding = if (windowSizeClass.isTablet()) 32.dp else 16.dp

    PotyTheme(darkTheme = true, dynamicColor = false) {
        ResponsiveNavBar {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                containerColor = MaterialTheme.colorScheme.secondary,
            ) { innerPadding ->
                if (isLandscape) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        ChargeHeaderSection(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f),
                            onNavigateBack = onNavigateBack,
                            contentPadding = contentPadding,
                            windowSizeClass = windowSizeClass
                        )

                        ChargeContentSection(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f),
                            state = state,
                            viewModel = viewModel,
                            contentPadding = contentPadding,
                            windowSizeClass = windowSizeClass
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        val headerWeight = if (windowSizeClass == WindowSizeClass.MediumTablet) {
                            1f
                        } else {
                            0.7f
                        }

                        ChargeHeaderSection(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(headerWeight),
                            onNavigateBack = onNavigateBack,
                            contentPadding = contentPadding,
                            windowSizeClass = windowSizeClass
                        )

                        ChargeContentSection(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(3.5f),
                            state = state,
                            viewModel = viewModel,
                            contentPadding = contentPadding,
                            windowSizeClass = windowSizeClass
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ChargeHeaderSection(
    modifier: Modifier,
    onNavigateBack: () -> Unit,
    contentPadding: Dp,
    windowSizeClass: WindowSizeClass
) {
    Box(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_scaffolding),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )


//        }
        BackButton()

        Card(
            modifier = Modifier
                .fillMaxSize(),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            ),
        ) {
            Column(
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = stringResource(R.string.charge_money),
                    style = if (windowSizeClass.isTablet()) {
                        MaterialTheme.typography.titleLarge
                    } else {
                        MaterialTheme.typography.titleSmallSemiBold
                    },
                    color = White
                )
            }
        }
    }
}

@Composable
fun ChargeContentSection(
    modifier: Modifier,
    state: ChargeScreenState,
    viewModel: ChargeScreenViewModel,
    contentPadding: Dp,
    windowSizeClass: WindowSizeClass,
    isPreview: Boolean = false
) {
    val context = LocalContext.current
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onBackground,
        ),
        shape = RoundedCornerShape(
            topStart = if (!windowSizeClass.isLandscape()) 30.dp else 0.dp,
            topEnd = 30.dp,
            bottomEnd = if (windowSizeClass.isLandscape()) 0.dp else 0.dp,
            bottomStart = if (windowSizeClass.isLandscape()) 30.dp else 0.dp
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        when (state.currentStep) {
            1 -> StepOne(
                amount = state.amount,
                updateAmount = { viewModel.onEvent(ChargeScreenEvent.UpdateAmount(it)) },
                nextStep = { viewModel.onEvent(ChargeScreenEvent.NextStep) },
                errorMessage = state.errorMessage,
                contentPadding = contentPadding,
                windowSizeClass = windowSizeClass,
                generatePaymentLink = { viewModel.onEvent(ChargeScreenEvent.GenerateNewLink) }
            )
            2 -> StepTwo(
                link = state.generatedLink,
                onCopyLink = {
                    if (!isPreview) {
                        viewModel.onEvent(ChargeScreenEvent.ShareLink(context = context))
                    }
                },
                onShareLink = {
                    if (!isPreview) {
                        viewModel.onEvent(ChargeScreenEvent.ShareLink(context = context))
                    }
                },
                contentPadding = contentPadding,
                windowSizeClass = windowSizeClass
            )
        }
    }
}

@Composable
fun StepOne(
    amount: String,
    updateAmount: (String) -> Unit,
    nextStep: () -> Unit,
    errorMessage: String,
    contentPadding: Dp,
    windowSizeClass: WindowSizeClass,
    generatePaymentLink: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NumberFieldWithLabel(
            label = stringResource(R.string.amount_to_charge),
            value = amount.toFloatOrNull() ?: 0f,
            onValueChange = { newValue -> updateAmount(newValue.toString()) }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                generatePaymentLink()
                nextStep() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = stringResource(R.string.next), color = MaterialTheme.colorScheme.onBackground)
        }
        if (errorMessage.isNotEmpty()) {
            ErrorMessage(message = errorMessage)
        }
    }
}

@Composable
fun StepTwo(
    link: String,
    onCopyLink: () -> Unit,
    onShareLink: () -> Unit,
    contentPadding: Dp,
    windowSizeClass: WindowSizeClass
) {
    Column(
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ReadOnlyTextFieldWithLabel(
            value = link,
            label = stringResource(R.string.link_generated_correctly),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onCopyLink() },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text(text = stringResource(R.string.copy_link), color = MaterialTheme.colorScheme.onBackground)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { onShareLink() },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text(text = stringResource(R.string.share_link), color = MaterialTheme.colorScheme.onBackground)
        }
    }
}


