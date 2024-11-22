package hci.mobile.poty.screens.addCard

import hci.mobile.poty.data.model.CardType

data class AddCardScreenState(
    val bank: String = "",
    val number: String = "",
    val owner: String = "",
    val cvv: String = "",
    val exp: String = "",
    val isValid: Boolean = false,
    val errorMessage: String? = null,
    val type: CardType = CardType.DEBIT
)
