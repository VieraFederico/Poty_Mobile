package hci.mobile.poty.screens.payment

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
import hci.mobile.poty.ui.theme.labelLargeLite
import hci.mobile.poty.ui.theme.titleSmallSemiBold
import hci.mobile.poty.utils.CompactDateFieldWithLabel
import hci.mobile.poty.utils.TextFieldWithLabel


@Preview
@Composable
fun PaymentScreenPreview(){
    PaymentScreen()
}
@Composable
fun PaymentScreen(viewModel: PaymentScreenViewModel = remember { PaymentScreenViewModel() }) {
    //val state by viewModel.state.collectAsState()

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
                                text="Enviar Dinero",
                                style = MaterialTheme.typography.titleMedium,
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
                        Button(
                            onClick = { },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .align(Alignment.CenterHorizontally),
                            shape = RoundedCornerShape(15.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Enviar por ",
                                    style = MaterialTheme.typography.labelLargeLite,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    text = "Link de Pago",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }

                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = { },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .align(Alignment.CenterHorizontally),
                            shape = RoundedCornerShape(15.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Enviar por ",
                                    style = MaterialTheme.typography.labelLargeLite,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    text = "Correo Electronico",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Envios Recientes"
                        )

                    }
                }
            }
        }
    }
}