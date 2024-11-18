package hci.mobile.poty.screens.charge
import ChargeScreenViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
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
import hci.mobile.poty.ui.components.BottomNavBar
import hci.mobile.poty.ui.theme.GreenDark
import hci.mobile.poty.ui.theme.White
import hci.mobile.poty.ui.theme.titleSmallSemiBold


@Preview
@Composable
fun DepositScreenPreview(){
    DepositScreen()
}
@Composable
fun DepositScreen(viewModel: ChargeScreenViewModel = remember { ChargeScreenViewModel() }) {
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
                        ) {
                            Text(
                                text = "Cobrar",
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

                    when (state.currentStep) {
                        ChargeStep.AMOUNT -> StepOne()
                        ChargeStep.LINK -> StepTwo()
                    }
                }
            }
        }
    }
}

@Composable
fun StepOne(
    /*amount: String,
    onAmountChanged: (String) -> Unit,
    NextStepClicked: () -> Unit*/
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Por favor, ingrese la cantidad a cobrar",
            style = MaterialTheme.typography.bodyLarge,
        )
        /*ThickTextFieldWithLabel(
            value = amount,
            onValueChange = { newValue ->
                onAmountChanged(newValue)
            }
        )

        Button(
            onClick = { NextStepClicked() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Siguiente", color = MaterialTheme.colorScheme.onBackground)
        }
*/
    }


}



@Composable
fun StepTwo(){

}