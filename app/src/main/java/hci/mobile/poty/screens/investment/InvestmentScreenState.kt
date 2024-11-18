package hci.mobile.poty.screens.investment

import hci.mobile.poty.classes.Transaction

data class InvestmentScreenState(
    val balance: Float = 0f,
    val invested: Float = 0f,
    val investAmount: Float = 0f,
    val withdrawAmount: Float = 0f,
    val errorMessage: String? = null,
    val transactions: List<Transaction> = emptyList()
)
