package hci.mobile.poty.screens.cvu

import UserResponse
import WalletDetailsResponse

data class CVUScreenState(
    val alias: String = "",
    val email: String = "",
    val cbu: String = "",
    val loading: Boolean = false,
    val error: String? = null
)

data class CVUDetailsResponse(
    val walletDetails: WalletDetailsResponse,
    val userDetails: UserResponse
)