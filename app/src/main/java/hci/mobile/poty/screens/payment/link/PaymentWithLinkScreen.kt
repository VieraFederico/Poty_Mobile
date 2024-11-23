package hci.mobile.poty.screens.payment.link
import android.util.Log
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.viewmodel.compose.viewModel
import hci.mobile.poty.MyApplication
import hci.mobile.poty.R
import hci.mobile.poty.data.model.Card
import hci.mobile.poty.data.model.LinkPaymentType
import hci.mobile.poty.data.network.model.NetworkLinkPayment
import hci.mobile.poty.screens.payment.PaymentScreenState
import hci.mobile.poty.screens.payment.PaymentScreenViewModel
import hci.mobile.poty.screens.payment.PaymentType
import hci.mobile.poty.ui.components.BackButton
import hci.mobile.poty.ui.components.PaymentBalanceCard
import hci.mobile.poty.ui.components.PaymentCardsCarousel
import hci.mobile.poty.ui.components.ResponsiveNavBar
import hci.mobile.poty.ui.theme.GreenLight
import hci.mobile.poty.ui.theme.GreyLight
import hci.mobile.poty.ui.theme.White
import hci.mobile.poty.ui.theme.titleMediumLite
import hci.mobile.poty.ui.theme.titleSmallSemiBold
import hci.mobile.poty.utils.ErrorMessage
import hci.mobile.poty.utils.ReadOnlyNumberFieldWithLabel
import hci.mobile.poty.utils.TextFieldWithLabel
import hci.mobile.poty.utils.WindowSizeClass
import hci.mobile.poty.utils.calculateWindowSizeClass
import hci.mobile.poty.utils.isLandscape
import hci.mobile.poty.utils.isTablet
import hci.mobile.poty.utils.isValidDate

@Composable
fun PaymentWithLinkScreen(
    viewModel: PaymentScreenViewModel = viewModel(factory = PaymentScreenViewModel.provideFactory(
        LocalContext.current.applicationContext as MyApplication
    )),
    mockWindowSizeClass: WindowSizeClass? = null,
    onNavigateToDashboard: () -> Unit = {},
    onNavigateToAddCard: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val windowSizeClass = mockWindowSizeClass ?: calculateWindowSizeClass()

    val contentPadding = when (windowSizeClass) {
        WindowSizeClass.MediumTablet,
        WindowSizeClass.MediumTabletLandscape -> 32.dp
        else -> 16.dp
    }

    val isLandscape = windowSizeClass in listOf(
        WindowSizeClass.MediumPhoneLandscape,
        WindowSizeClass.MediumTabletLandscape
    )

    PotyTheme(darkTheme = true, dynamicColor = false) {
        ResponsiveNavBar(
            onNavigate = { /* Handle navigation */ },
            mockWindowSizeClass = mockWindowSizeClass,
        ) {
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
                    Box(
                        modifier = Modifier
                            .weight(0.53f)
                            .fillMaxHeight()
                            .padding(contentPadding)
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
                            .padding(contentPadding)
                    ) {
                        ContentSection(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = contentPadding,
                            state = state,
                            viewModel = viewModel,
                            windowSizeClass = windowSizeClass,
                            onNavigateToDashboard = onNavigateToDashboard,

                            onNavigateToAddCard = onNavigateToAddCard
                        )
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(contentPadding)
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
                            .weight(3.5f)
                            .fillMaxWidth()
                            .padding(contentPadding)
                    ) {
                        ContentSection(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = contentPadding,
                            state = state,
                            viewModel = viewModel,
                            windowSizeClass = windowSizeClass,
                            onNavigateToDashboard = onNavigateToDashboard,
                            onNavigateToAddCard = onNavigateToAddCard
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
    link: String,
    onLinkChange: (String) -> Unit,
    onNext: () -> Unit,
    errorMessage: String,
    validateLink: () -> Boolean,
    windowSizeClass: WindowSizeClass,
    fetchPaymentData: (String) -> Unit,
    onNumberChange: (Float) -> Unit,

) {
    var localLink by remember { mutableStateOf(link) }

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
            label = stringResource(R.string.enter_payment_link),
            value = localLink,
            onValueChange = {
                localLink = it
                onLinkChange(it)
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (validateLink()) {
                    fetchPaymentData(localLink)

                    onNext()
                }


            },
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
    number: Float,
    balance: Float,
    creditCards: List<Card>,
    selectedCard: Card? = null,
    onCardSelected: (Card) -> Unit,
    paymentMethod: LinkPaymentType,
    onPaymentTypeChange: (LinkPaymentType) -> Unit,
    onNavigateToAddCard: () -> Unit,
    onDeleteCard: (Int) -> Unit,
    onSubmit: () -> Unit,
    errorMessage: String,
    description: String,
    onDescriptionChange: (String) -> Unit,
    windowSizeClass: WindowSizeClass,
    onSettlePayment: (String) -> Unit,
    linkUuid: String,
    onNavigateToDashboard: () -> Unit,
    validateBalance: () -> Boolean,
    validateDescription: () -> Boolean,

    ) {

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
                            bottom = 15.dp
                        )
                        .fillMaxSize()
                        .weight(1f),
                    verticalArrangement = Arrangement.Center,
                ) {
                    ReadOnlyNumberFieldWithLabel(

                        label = stringResource(R.string.amount_to_send),
                        value = number
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    TextFieldWithLabel(
                        label = stringResource(R.string.description),
                        value = description,
                        onValueChange = onDescriptionChange,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { onSubmit()
                                  },
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
                        LinkPaymentType.CARD -> {
                            PaymentCardsCarousel(
                                creditCards = creditCards,
                                selectedCard = localSelectedCard,
                                onCardSelected = onCardSelected,
                                onNavigateToAddCard = onNavigateToAddCard,
                                onDeleteCard = onDeleteCard,
                                isTiny = true
                            )
                        }

                        LinkPaymentType.BALANCE -> {
                            PaymentBalanceCard(balance)
                        }

                        LinkPaymentType.LINK -> {}
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
                ReadOnlyNumberFieldWithLabel(
                    label = stringResource(R.string.amount_to_send),
                    value = number
                )

                when (windowSizeClass) {
                    WindowSizeClass.MediumTabletLandscape -> Spacer(modifier = Modifier.height(0.dp))
                    else ->Spacer(modifier = Modifier.height(16.dp))
                }

                TextFieldWithLabel(
                    label = stringResource(R.string.description),
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
                    LinkPaymentType.CARD -> {
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

                    LinkPaymentType.BALANCE -> {
                        PaymentBalanceCard(balance)
                    }

                    LinkPaymentType.LINK -> {}
                }

                Button(
                    onClick = {
                        if(validateBalance() && validateDescription()) {
                            onSettlePayment(linkUuid)
                            onNavigateToDashboard()
                        }
                        },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = stringResource(R.string.send), color = MaterialTheme.colorScheme.onBackground)
                }

                if (errorMessage.isNotEmpty()) {
                    ErrorMessage(message = errorMessage)
                }
            }
        }
    }
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
            BackButton()

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
                    if(windowSizeClass.isLandscape() && !windowSizeClass.isTablet()){
                        Text(
                            text=stringResource(R.string.pay_money),
                            style = MaterialTheme.typography.titleSmallSemiBold,
                            color = White
                        )

                    }else {
                        Text(
                            text = stringResource(R.string.pay_money),
                            style = MaterialTheme.typography.titleMedium,
                            color = White
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text=stringResource(R.string.using_payment_link),
                        style = MaterialTheme.typography.titleSmall,
                        softWrap = true,
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


    onNavigateToDashboard: () -> Unit,
    onNavigateToAddCard: () -> Unit

) {
    val isLandscape = windowSizeClass in listOf(
        WindowSizeClass.MediumPhoneLandscape,
        WindowSizeClass.MediumTabletLandscape
    )


    Card(
        modifier = Modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onBackground,
        ),
        shape =  RoundedCornerShape(
            topStart = 30.dp,
            topEnd =     if (!windowSizeClass.isLandscape()) 30.dp else 0.dp,
            bottomStart = if (windowSizeClass.isLandscape()) 30.dp else 0.dp,
            bottomEnd =  0.dp,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            when (state.currentStep) {
                1 -> StepOne(
                    link = state.paymentLink,
                    onLinkChange = { viewModel.updateLink(it) },
                    onNext = { viewModel.nextStep() },
                    errorMessage = state.errorMessage,
                    windowSizeClass = windowSizeClass,
                    validateLink = { viewModel.validateLink() },
                    fetchPaymentData = { viewModel.getPaymentData(it) },
                    onNumberChange = { viewModel.updateAmount(it) },


                    )
                2 -> StepTwo(
                    number = state.amount,
                    balance = state.balance,
                    creditCards = state.creditCards,
                    selectedCard = state.selectedCard,
                    onCardSelected = { viewModel.selectCard(it) },
                    paymentMethod = state.type,
                    onPaymentTypeChange = {viewModel.onPaymentTypeChange(it)},
                    onNavigateToAddCard = { onNavigateToAddCard()  },
                    onDeleteCard = { viewModel.onDeleteCard(it) },
                    onSubmit = { viewModel.onSubmitPayment() },
                    errorMessage = state.errorMessage,
                    description = state.description,
                    onDescriptionChange = {viewModel.onDescriptionChange(it)},
                    windowSizeClass = windowSizeClass,
                    onSettlePayment = { linkUuid -> viewModel.settlePayment(linkUuid) },
                    linkUuid = state.paymentLink,
                    onNavigateToDashboard = { onNavigateToDashboard() },
                    validateBalance = { viewModel.validateBalance() },
                    validateDescription = { viewModel.validateDescription() }
                    )



            }
        }
    }
}


@Composable
fun SelectOptionTextButton(
    selectedOption: LinkPaymentType,
    onOptionSelected: (LinkPaymentType) -> Unit
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
            onClick = { onOptionSelected(LinkPaymentType.CARD) },
            modifier = Modifier.padding(end = 8.dp)
        ) {
            Text(
                text = "Tarjeta",
                color = if (selectedOption == LinkPaymentType.CARD) GreenLight else GreyLight
            )
        }

        TextButton(
            onClick = { onOptionSelected(LinkPaymentType.BALANCE) },
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(
                text = "Balance",
                color = if (selectedOption == LinkPaymentType.BALANCE) GreenLight else GreyLight
            )
        }
    }
}