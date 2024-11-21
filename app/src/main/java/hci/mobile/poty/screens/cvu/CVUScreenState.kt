package hci.mobile.poty.screens.cvu


import hci.mobile.poty.classes.User
import hci.mobile.poty.classes.WalletDetailsResponse

data class CVUScreenState(
    val alias: String = "",
    val email: String = "",
    val cbu: String = "",
    val loading: Boolean = false,
    val error: String? = null
)

data class CVUDetailsResponse(
    val walletDetails: WalletDetailsResponse,
    val userDetails: User
)