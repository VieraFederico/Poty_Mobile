
package hci.mobile.poty.classes

import java.time.LocalDateTime

data class Transaction(
    val id: String,
    val type: TransactionType,
    val amount: Float,
    val description: String,
    val timestamp: LocalDateTime,
    val status: TransactionStatus = TransactionStatus.COMPLETED
)
