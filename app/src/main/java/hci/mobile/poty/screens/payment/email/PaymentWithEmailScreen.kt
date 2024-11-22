package hci.mobile.poty.screens.payment.email

import hci.mobile.poty.classes.CardResponse
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hci.mobile.poty.ui.theme.PotyTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.viewmodel.compose.viewModel
import hci.mobile.poty.MyApplication
import hci.mobile.poty.R
import hci.mobile.poty.screens.payment.PaymentHistory
import hci.mobile.poty.screens.payment.PaymentScreenState
import hci.mobile.poty.screens.payment.PaymentScreenViewModel
import hci.mobile.poty.screens.payment.PaymentType
import hci.mobile.poty.ui.components.PaymentBalanceCard
import hci.mobile.poty.ui.components.PaymentCardsCarousel
import hci.mobile.poty.ui.components.ResponsiveNavBar
import hci.mobile.poty.ui.theme.GreenDark
import hci.mobile.poty.ui.theme.GreenLight
import hci.mobile.poty.ui.theme.GreyDark
import hci.mobile.poty.ui.theme.GreyLight
import hci.mobile.poty.ui.theme.White
import hci.mobile.poty.ui.theme.labelLargeLite
import hci.mobile.poty.ui.theme.titleMediumLite
import hci.mobile.poty.utils.ErrorMessage
import hci.mobile.poty.utils.NumberFieldWithLabel
import hci.mobile.poty.utils.TextFieldWithLabel
import hci.mobile.poty.utils.WindowSizeClass
import hci.mobile.poty.utils.calculateWindowSizeClass

@Preview
@Composable
fun PaymentWithEmailScreenPreview(){
    PaymentWithEmailScreen()
}
@Composable
fun PaymentWithEmailScreen(
    viewModel: PaymentScreenViewModel = viewModel(factory = PaymentScreenViewModel.provideFactory(
        LocalContext.current.applicationContext as MyApplication
    )),
    mockWindowSizeClass: WindowSizeClass? = null,
    onNavigateToDashboard: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    val windowSizeClass = mockWindowSizeClass ?: calculateWindowSizeClass()

    val contentPadding = if (windowSizeClass == WindowSizeClass.MediumTablet ||
        windowSizeClass == WindowSizeClass.MediumTabletLandscape) 32.dp else 16.dp
    val isLandscape = windowSizeClass == WindowSizeClass.MediumPhoneLandscape ||
            windowSizeClass == WindowSizeClass.MediumTabletLandscape

    PotyTheme(darkTheme = true, dynamicColor = false) {
        ResponsiveNavBar(
            onNavigate = { /* Handle navigation */ },
            mockWindowSizeClass = mockWindowSizeClass
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                containerColor = MaterialTheme.colorScheme.secondary,
            ) { innerPadding ->
                if(isLandscape) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        ) {
                            HeaderSection(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = contentPadding,
                                state = state,
                                windowSizeClass = windowSizeClass
                            )
                        }

                        Box(
                            modifier = Modifier
                                .weight(2f)
                                .fillMaxHeight()
                        ) {
                            ContentSection(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = contentPadding,
                                state = state,
                                viewModel = viewModel,
                                windowSizeClass = windowSizeClass,
                                topStart = 30.dp,
                                bottomStart = 30.dp,
                                onNavigateToDashboard = onNavigateToDashboard // Añadido

                            )
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(0.8f)
                                .fillMaxWidth()
                        ) {
                            HeaderSection(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = contentPadding,
                                state = state,
                                windowSizeClass = windowSizeClass
                            )
                        }

                        Box(
                            modifier = Modifier
                                .weight(3f)
                                .fillMaxWidth()
                        ) {
                            ContentSection(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = contentPadding,
                                state = state,
                                viewModel = viewModel,
                                windowSizeClass = windowSizeClass,
                                topStart = 30.dp,
                                bottomStart = 30.dp,
                                onNavigateToDashboard = onNavigateToDashboard // Añadido

                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StepOne(
    email: String,
    onEmailChange: (String) -> Unit,
    onNext: () -> Unit,
    errorMessage: String,
    windowSizeClass: WindowSizeClass,
) {
    var localEmail by remember { mutableStateOf(email) }

    Column(
        modifier = Modifier
            .padding(horizontal =
            when (windowSizeClass) {
                WindowSizeClass.MediumPhoneLandscape,
                WindowSizeClass.MediumTabletLandscape -> 150.dp
                else -> 16.dp
            },)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        TextFieldWithLabel(
            label = "Ingrese el Correo Electronico al cual desea enviar dinero",
            value = localEmail,
            onValueChange = {
                localEmail = it
                onEmailChange(it)
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
    number: Double,
    balance: Float,
    onNumberChange: (Float) -> Unit,
    creditCards: List<CardResponse>,
    selectedCard: CardResponse? = null,
    onCardSelected: (CardResponse) -> Unit,
    paymentMethod: PaymentType,
    onPaymentTypeChange: (PaymentType) -> Unit,
    onNavigateToAddCard: () -> Unit,
    onDeleteCard: (Int) -> Unit,
    onSubmit: () -> Unit,
    errorMessage: String,
    description: String,
    onDescriptionChange: (String) -> Unit,
    windowSizeClass: WindowSizeClass,
    ) {
    var localNumber by remember { mutableStateOf(number) }
    var localSelectedCard by remember { mutableStateOf(selectedCard) }
    var localPaymentMethod by remember { mutableStateOf(paymentMethod) }
    when(windowSizeClass) {
        WindowSizeClass.MediumPhoneLandscape -> {
            Row() {
                Column(
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            top = 5.dp,
                            end = 16.dp,
                            bottom = 0.dp
                        )
                        .fillMaxSize()
                        .weight(1f),
                    verticalArrangement = Arrangement.Center,
                ) {
                    NumberFieldWithLabel(
                        label = "Monto a Enviar",
                        value = localNumber.toFloat(),
                        onValueChange = {
                            localNumber = it.toDouble()
                            onNumberChange(it)
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    TextFieldWithLabel(
                        label = "Descripcion",
                        value = description,
                        onValueChange = onDescriptionChange,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { onSubmit() },
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
                Column(
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            top = when (windowSizeClass) {
                                WindowSizeClass.MediumTabletLandscape -> 16.dp
                                else -> 0.dp
                            },
                            end = 16.dp,
                            bottom = 16.dp
                        )
                        .fillMaxSize()
                        .weight(1f),
                    verticalArrangement = Arrangement.Center,
                ) {
                    SelectOptionTextButton(
                        selectedOption = localPaymentMethod,
                        onOptionSelected = { selectedOption ->
                            localPaymentMethod = selectedOption
                            onPaymentTypeChange(selectedOption)
                        }
                    )

                    when (localPaymentMethod) {
                        PaymentType.CARD -> {
                            PaymentCardsCarousel(
                                creditCards = creditCards,
                                selectedCard = localSelectedCard,
                                onCardSelected = onCardSelected,
                                onNavigateToAddCard = onNavigateToAddCard,
                                onDeleteCard = onDeleteCard,
                                isTiny = true
                            )
                        }

                        PaymentType.BALANCE -> {
                            PaymentBalanceCard(balance)
                        }
                    }
                }
            }
        }
        else -> {
            Column(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = when (windowSizeClass) {
                            WindowSizeClass.MediumTabletLandscape -> 10.dp

                            else -> 0.dp
                        },
                        end = 16.dp,
                        bottom = when (windowSizeClass) {
                            WindowSizeClass.MediumTabletLandscape -> 0.dp

                            else -> 16.dp
                        },
                    )
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
            ) {
                NumberFieldWithLabel(
                    label = "Monto a Enviar",
                    value = localNumber.toFloat(),
                    onValueChange = {
                        localNumber = it.toDouble()
                        onNumberChange(it)
                    }
                )
                when (windowSizeClass) {
                    WindowSizeClass.MediumTabletLandscape -> Spacer(modifier = Modifier.height(0.dp))
                    else ->Spacer(modifier = Modifier.height(16.dp))
                }

                TextFieldWithLabel(
                    label = "Descripcion",
                    value = description,
                    onValueChange = onDescriptionChange,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))
                Box() {
                    SelectOptionTextButton(
                        selectedOption = localPaymentMethod,
                        onOptionSelected = { selectedOption ->
                            localPaymentMethod = selectedOption
                            onPaymentTypeChange(selectedOption)
                        }
                    )
                }

                when (localPaymentMethod) {
                    PaymentType.CARD -> {
                        PaymentCardsCarousel(
                            creditCards = creditCards,
                            selectedCard = localSelectedCard,
                            onCardSelected = onCardSelected,
                            onNavigateToAddCard = onNavigateToAddCard,
                            onDeleteCard = onDeleteCard,
                            isTiny = when (windowSizeClass) {
                                WindowSizeClass.MediumPhone -> true
                                else ->false
                            }
                        )
                    }

                    PaymentType.BALANCE -> {
                        PaymentBalanceCard(balance)
                    }
                }

                Button(
                    onClick = { onSubmit() },
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
    }
}

@Composable
fun SelectOptionTextButton(
    selectedOption: PaymentType,
    onOptionSelected: (PaymentType) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(
                start = 16.dp,
                top = 5.dp,
                end = 16.dp,
                bottom = 0.dp
            )
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        TextButton(
            onClick = { onOptionSelected(PaymentType.CARD) },
            modifier = Modifier.padding(end = 8.dp)
        ) {
            Text(
                text = "Tarjeta",
                color = if (selectedOption == PaymentType.CARD) GreenLight else GreyLight
            )
        }

        TextButton(
            onClick = { onOptionSelected(PaymentType.BALANCE) },
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(
                text = "Balance",
                color = if (selectedOption == PaymentType.BALANCE) GreenLight else GreyLight
            )
        }
    }
}


@Preview
@Composable
fun SelectOptionButtonPreview() {
    var selectedOption by remember { mutableStateOf(PaymentType.CARD) }
    SelectOptionTextButton(
        selectedOption = selectedOption,
        onOptionSelected = { selectedOption = it }
    )

    Text("Opción seleccionada: $selectedOption")
}

@Composable
fun HeaderSection(
    modifier: Modifier,
    contentPadding: Dp,
    state: PaymentScreenState,
    isPhonePortrait: Boolean = false,
    windowSizeClass: WindowSizeClass = WindowSizeClass.MediumPhone
) {
    Box(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_scaffolding),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top
        ) {
            IconButton(
                onClick = { /*Cuando enseñen navegacion xddd*/ },
                modifier = Modifier.padding(
                    start = contentPadding,
                    top = contentPadding,
                    end = contentPadding,
                    bottom = 0.dp  // Remove bottom padding
                )
            ) {
                Surface(
                    shape = CircleShape,
                    color = GreenDark,
                    modifier = Modifier.size(35.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Go Back",
                        tint = White
                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = contentPadding,
                        top = 0.dp,
                        end = contentPadding,
                        bottom = contentPadding
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                ),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),  // Reduced padding
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text="Enviar Dinero",
                        style = MaterialTheme.typography.titleMedium,
                        color = White
                    )
                    Text(
                        text="Por Correo Electronico",
                        style = MaterialTheme.typography.titleMediumLite,
                        color = White
                    )
                }
            }
        }
    }
}

@Composable
fun ContentSection(
    modifier: Modifier,
    contentPadding: Dp,
    state: PaymentScreenState,
    viewModel: PaymentScreenViewModel,
    windowSizeClass: WindowSizeClass,
    topStart: Dp = 0.dp,
    topEnd: Dp = 0.dp,
    bottomStart: Dp = 0.dp,
    bottomEnd: Dp = 0.dp,
    onNavigateToDashboard: () -> Unit

) {

    Card(
        modifier = Modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onBackground,
        ),
        shape = RoundedCornerShape(
            topStart = 30.dp,
            topEnd = 30.dp,
            bottomEnd = 0.dp,
            bottomStart = 0.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 15.dp,
                    end = 15.dp,
                    bottom = 15.dp,
                )
        ) {
            when (state.currentStep) {
                1 -> StepOne(
                    email = state.email,
                    onEmailChange = { viewModel.updateEmail(it) },
                    onNext = { viewModel.nextStep() },
                    errorMessage = state.errorMessage,
                    windowSizeClass = windowSizeClass

                )
                2 -> StepTwo(
                    number = state.request.amount,
                    balance = state.balance,
                    onNumberChange = { viewModel.updateAmount(it.toDouble()) },
                    creditCards = state.creditCards,
                    selectedCard = state.selectedCard,
                    onCardSelected = { viewModel.selectCard(it) },
                    paymentMethod = state.type,
                    onPaymentTypeChange = {viewModel.onPaymentTypeChange(it)},
                    onNavigateToAddCard = { /* Hay que agregar esto xd */ },
                    onDeleteCard = { viewModel.onDeleteCard(it) },
                    onSubmit = { viewModel.onSubmitPayment()
                               onNavigateToDashboard()},
                    errorMessage = state.errorMessage,
                    description = state.description,
                    onDescriptionChange = {viewModel.onDescriptionChange(it)},
                    windowSizeClass = windowSizeClass
                )
            }
        }
    }
}

@Preview(
    name = "Medium Tablet Portrait",
    device = "spec:width=800dp,height=1280dp",
    showBackground = true
)
@Composable
fun MediumTabletPortraitPreview() {
    PaymentWithEmailScreen(
        mockWindowSizeClass = WindowSizeClass.MediumTablet
    )
}

@Preview(
    name = "Medium Tablet Landscape",
    device = "spec:width=1280dp,height=800dp",
    showBackground = true
)
@Composable
fun MediumTabletLandscapePreview() {
    PaymentWithEmailScreen(

        mockWindowSizeClass = WindowSizeClass.MediumTabletLandscape
    )
}
@Preview(
    name = "Medium Phone Portrait",
    device = "spec:width=411dp,height=914dp",
    showBackground = true
)
@Composable
fun MediumPhonePortraitPreview() {
    PaymentWithEmailScreen(
        mockWindowSizeClass = WindowSizeClass.MediumPhone
    )
}

@Preview(
    name = "Medium Phone Landscape",
    device = "spec:width=914dp,height=411dp",
    showBackground = true
)
@Composable
fun MediumPhoneLandscapePreview() {
    PaymentWithEmailScreen(
        mockWindowSizeClass = WindowSizeClass.MediumPhoneLandscape
    )
}