package hci.mobile.poty.screens.addCard

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
import androidx.compose.foundation.layout.width
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
import hci.mobile.poty.classes.CreditCard
import hci.mobile.poty.ui.components.BottomNavBar
import hci.mobile.poty.ui.components.FullCreditCardView
import hci.mobile.poty.ui.theme.GreenDark
import hci.mobile.poty.ui.theme.White
import hci.mobile.poty.ui.theme.titleSmallSemiBold
import hci.mobile.poty.utils.CompactDateFieldWithLabel
import hci.mobile.poty.utils.TextFieldWithLabel


@Preview
@Composable
fun AddCardScreenPreview(){
    AddCardScreen()
}
@Composable
fun AddCardScreen(viewModel: AddCardScreenViewModel = remember { AddCardScreenViewModel() }) {
    val state by viewModel.state.collectAsState()

    PotyTheme(darkTheme = true, dynamicColor = false) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.secondary,
            bottomBar = { BottomNavBar { } },
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
                    // Background image
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
                                text="Agregar Nueva",
                                style = MaterialTheme.typography.titleSmallSemiBold,
                                color = White
                            )
                            Text(
                                text="Tarjeta",
                                style = MaterialTheme.typography.titleSmallSemiBold,
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

                        FullCreditCardView(
                            creditCard = CreditCard(
                                bank = "Banco Royale", //TODO:Cambiar Cuando nos den la API
                                number = state.number,
                                owner = state.owner,
                                cvv = state.cvv,
                                exp = state.exp
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        TextFieldWithLabel(
                            label = "Número de Tarjeta",
                            value = state.number,
                            onValueChange = { viewModel.onNumberChange(it) },
                            maxLength = 16
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        TextFieldWithLabel(
                            label = "Nombre del Responsable",
                            value = state.owner,
                            onValueChange = { viewModel.onOwnerChange(it) }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row() {
                            Column(modifier = Modifier.weight(1f)) {
                                TextFieldWithLabel(
                                    label = "CVV",
                                    value = state.cvv,
                                    onValueChange = { viewModel.onCVVChange(it) },
                                    maxLength = 3
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                CompactDateFieldWithLabel(
                                    label = "Fecha de Venc.",
                                    value = state.exp,
                                    onValueChange = { viewModel.onExpDateChange(it) }
                                )
                            }
                        }

                        state.errorMessage?.let {
                            Text(
                                text = it,
                                color = Color.Red,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = { viewModel.onAddCardClick() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .align(Alignment.CenterHorizontally),
                        ) {
                            Text(
                                text = "Agregar Tarjeta",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
            }
        }
    }
}