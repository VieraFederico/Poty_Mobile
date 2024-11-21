package hci.mobile.poty.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hci.mobile.poty.classes.Transaction
import hci.mobile.poty.classes.TransactionStatus
import hci.mobile.poty.classes.TransactionType
import hci.mobile.poty.ui.theme.GreenDark
import hci.mobile.poty.ui.theme.PotyTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Preview
@Composable
fun TrasactionHistoryPreview(){
    val sampleTransactions = listOf(
        Transaction(
            id = "1",
            type = TransactionType.DEPOSIT,
            amount = 100.00,
            description = "Sandwich del Buen Libro",
            timestamp = LocalDateTime.now(),
            status = TransactionStatus.COMPLETED
        ),
        Transaction(
            id = "2",
            type = TransactionType.WITHDRAWAL,
            amount = 50.00,
            description = "Curso de Kotlin",
            timestamp = LocalDateTime.now().minusHours(2),
            status = TransactionStatus.COMPLETED
        ),
        // Add more transactions as needed
    )
    PotyTheme { TransactionHistory(transactions = sampleTransactions) }
}

@Composable
fun TransactionHistory(
    transactions: List<Transaction>,
    modifier: Modifier = Modifier,
    useWhite: Boolean = false
) {

    val containerColor = if(useWhite) Color.White else GreenDark
    val textColor = if(useWhite) Color.Black else Color.White

    Column(modifier = modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start=16.dp,end=16.dp,bottom=8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(
            containerColor = containerColor
            )
        ) {
            Text(
                text = "Historial de Movimientos",
                style = MaterialTheme.typography.bodyLarge,
                color = textColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom=16.dp,start=16.dp,end=16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(transactions) { transaction ->
                TransactionCard(transaction = transaction, useWhite = useWhite)
            }
        }
    }
}


@Composable
fun TransactionCard(transaction: Transaction,
    useWhite: Boolean = false
    ) {
    val containerColor = if(useWhite) Color.White else GreenDark
    val textColor = if(useWhite) Color.Black else Color.White
    // Use your GreenDark color for the card background
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Transaction Icon and Description
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = when (transaction.type) {
                        TransactionType.DEPOSIT -> Icons.Default.KeyboardArrowDown
                        TransactionType.WITHDRAWAL -> Icons.Default.KeyboardArrowUp
                        TransactionType.TRANSFER -> Icons.Default.KeyboardArrowUp
                    },
                    contentDescription = transaction.type.name,
                    tint = when (transaction.type) {
                        TransactionType.DEPOSIT -> Color.Green
                        TransactionType.WITHDRAWAL -> Color.Red
                        TransactionType.TRANSFER -> Color.Blue
                    }
                )

                Column {
                    // Set all text to white
                    Text(
                        text = transaction.description,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        color = textColor
                    )
                    Text(
                        text = transaction.timestamp.format(
                            DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm")
                        ),
                        style = MaterialTheme.typography.bodySmall,
                        color = textColor
                    )
                }
            }

            // Amount and Status
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "${if (transaction.type == TransactionType.DEPOSIT) "+" else "-"}$${String.format("%.2f", transaction.amount)}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White, // Text color set to white
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = transaction.status.name,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White // Text color set to white
                )
            }
        }
    }
}

