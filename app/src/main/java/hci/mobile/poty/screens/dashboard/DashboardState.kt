package hci.mobile.poty.screens.dashboard


import androidx.compose.runtime.mutableStateListOf
import hci.mobile.poty.classes.Transaction
import hci.mobile.poty.classes.TransactionStatus
import hci.mobile.poty.classes.TransactionType
import hci.mobile.poty.data.model.Card
import java.time.LocalDateTime

data class DashboardState(
    val userName: String = "",
    val balance: Float = 0f,
    val spent: Float = 0f,
    val isBalanceVisible: Boolean = true,
    val creditCards: List<Card> = emptyList(),
    val errorMessage: String? = null,


    val transactions: List<Transaction> = mutableStateListOf(
        Transaction(
            id = "1",
            type = TransactionType.DEPOSIT,
            amount = 100.00f,
            description = "Sandwich del Buen Libro",
            timestamp = LocalDateTime.now(),
            status = TransactionStatus.COMPLETED
        ),
        Transaction(
            id = "2",
            type = TransactionType.WITHDRAWAL,
            amount = 50.00f,
            description = "Curso de Kotlin",
            timestamp = LocalDateTime.now().minusHours(2),
            status = TransactionStatus.COMPLETED
        ),
    ),
)

