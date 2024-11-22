package hci.mobile.poty.screens.payment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hci.mobile.poty.classes.PaymentResponse
import hci.mobile.poty.ui.theme.PotyTheme
import hci.mobile.poty.ui.theme.White

@Composable
fun PaymentHistory(
    payments: List<PaymentResponse>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end=8.dp,bottom = 8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = "Payment History",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }

        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .padding(end = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(payments) { payment ->
                PaymentCard(payment = payment)
            }
        }
    }
}

@Composable
fun PaymentCard(payment: PaymentResponse) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = when (payment.type) {
                        "CARD" -> Icons.Default.KeyboardArrowUp
                        else -> Icons.Default.KeyboardArrowUp
                    },
                    contentDescription = payment.type,
                    tint = White
                )

                Column {
                    Text(
                        text = "Fecha: ${payment.createdAt}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        color = White
                    )
                    Text(
                        text = when (payment.type) {
                            "CARD" -> "Pagado con Tarjeta"
                            "BALANCE" -> "Pagado con Balance"
                            else -> "Tipo de pago desconocido"
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = White
                    )
                    if(payment.type=="CARD") {
                        payment.card?.let {
                            Text(
                                text = "**** **** **** ${it.number.takeLast(4)}",
                                style = MaterialTheme.typography.bodySmall,
                                color = White
                            )
                        }
                    }
                }
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = if (payment.pending) {
                        "Pendiente"
                    } else {
                        "Completado"
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = if (payment.pending) Color.Yellow else Color.Green
                )

                Text(
                    text = "$${String.format("%.2f", payment.amount)}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview
@Composable
fun PaymentHistoryPreview() {
    val payments = listOf(
        PaymentResponse(
            id = 1,
            type = "CARD",
            amount = 150.0,
            balanceBefore = 0.0,
            balanceAfter = 0.0,
            pending = true,
            linkUuid = null,
            createdAt = "2023-12-23",
            updatedAt = "2023-12-23",
            card = hci.mobile.poty.classes.CardDetails(
                id = 1,
                number = "1234567890123452",
                expirationDate = "04/28",
                fullName = "John Doe",
                type = "CREDIT"
            )
        )
    )
    PotyTheme() {
        PaymentHistory(payments = payments)
    }
}
