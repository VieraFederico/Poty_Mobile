package hci.mobile.poty.screens.dashboard

import CardResponse
import androidx.compose.runtime.mutableStateListOf
import hci.mobile.poty.classes.CreditCard
import hci.mobile.poty.classes.Transaction
import hci.mobile.poty.classes.TransactionStatus
import hci.mobile.poty.classes.TransactionType
import java.time.LocalDateTime

data class DashboardState(
    val userName: String = "James Bond",
    val balance: Float = 12750.60f,
    val spent: Float = 6451f,
    val isBalanceVisible: Boolean = true,
    val creditCards: List<CardResponse> = mutableStateListOf(
        CardResponse(
            id = 1,
            number = "1234567812345678",
            type = "Crédito",
            fullName = "James Bond",
            expirationDate = "12/24"
        ),
        CardResponse(
            id = 2,
            number = "8765432187654321",
            type = "Débito",
            fullName = "James Bond",
            expirationDate = "08/25"
        )
    ),
    val transactions: List<Transaction> = mutableStateListOf(
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
    ),
)

