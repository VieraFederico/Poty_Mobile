package hci.mobile.poty.screens.Dashboard

import hci.mobile.poty.classes.CreditCard

data class DashboardState(
    val userName: String = "James Bond",
    val balance: Float = 10f,
    val isBalanceVisible: Boolean = true,
    val creditCards: List<CreditCard> = emptyList()
)
