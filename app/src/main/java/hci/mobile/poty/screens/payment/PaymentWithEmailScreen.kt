package hci.mobile.poty.screens.payment

import CardResponse
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
import androidx.compose.ui.res.painterResource
import hci.mobile.poty.R
import hci.mobile.poty.ui.components.BottomNavBar
import hci.mobile.poty.ui.components.PaymentBalanceCard
import hci.mobile.poty.ui.components.PaymentCardsCarousel
import hci.mobile.poty.ui.components.RecipientCard
import hci.mobile.poty.ui.theme.GreenDark
import hci.mobile.poty.ui.theme.GreenLight
import hci.mobile.poty.ui.theme.GreyLight
import hci.mobile.poty.ui.theme.White
import hci.mobile.poty.ui.theme.labelLargeLite
import hci.mobile.poty.ui.theme.titleMediumLite
import hci.mobile.poty.utils.ErrorMessage
import hci.mobile.poty.utils.NumberFieldWithLabel
import hci.mobile.poty.utils.TextFieldWithLabel

@Preview
@Composable
fun PaymentWithEmailScreenPreview(){
    PaymentWithEmailScreen()
}
@Composable
fun PaymentWithEmailScreen(viewModel: PaymentScreenViewModel = remember { PaymentScreenViewModel() }) {
    val state by viewModel.state.collectAsState()

    PotyTheme(darkTheme = true, dynamicColor = false) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.secondary,
            bottomBar = { BottomNavBar(onNavigate = { }) }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Top
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),

                    ) {
                    Image(
                        painter = painterResource(id = R.drawable.background_scaffolding),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    IconButton(
                        onClick = { /*Cuando enseñen navegacion xddd*/ },
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
                            .fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent
                        ),
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ){
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

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(3.5f),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.onBackground,
                    ),
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp, bottomEnd = 0.dp, bottomStart = 0.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(15.dp)
                    ) {
                        when (state.currentStep) {
                            1 -> StepOne(
                                email = state.email,
                                onEmailChange = { viewModel.updateEmail(it) },
                                onNext = { viewModel.nextStep() },
                                errorMessage = state.errorMessage
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
                                onSubmit = { viewModel.onSubmitPayment() },
                                errorMessage = state.errorMessage
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
    errorMessage: String
    ) {
    var localEmail by remember { mutableStateOf(email) }

    Column(
        modifier = Modifier
            .padding(16.dp)
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
    balance: Double,
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
) {
    var localNumber by remember { mutableStateOf(number) }
    var localSelectedCard by remember { mutableStateOf(selectedCard) }
    var localPaymentMethod by remember { mutableStateOf(paymentMethod) }
    Column(
        modifier = Modifier
            .padding(16.dp)
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
                    onDeleteCard = onDeleteCard
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

@Composable
fun SelectOptionTextButton(
    selectedOption: PaymentType,
    onOptionSelected: (PaymentType) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(16.dp)
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