package hci.mobile.poty.screens.AddCard

data class AddCardScreenState(
    val bank: String = "",
    val number: String = "",
    val owner: String = "",
    val cvv: String = "",
    val exp: String = "",
    val isValid: Boolean = false,
    val errorMessage: String? = null
)
