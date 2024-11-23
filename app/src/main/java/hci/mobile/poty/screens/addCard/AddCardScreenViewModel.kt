package hci.mobile.poty.screens.addCard

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import hci.mobile.poty.MyApplication
import hci.mobile.poty.R
import hci.mobile.poty.classes.CreditCard
import hci.mobile.poty.data.model.Card
import hci.mobile.poty.data.repository.WalletRepository
import hci.mobile.poty.screens.deposit.DepositScreenViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar


class AddCardScreenViewModel(
    private val walletRepository: WalletRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(AddCardScreenState())
    val state: StateFlow<AddCardScreenState> = _state

    var onAddCardSuccess: (() -> Unit)? = null // Callback para navegar al Dashboard

    fun onAddCardClick(context: Context) {
        val currentState = _state.value

        if (currentState.number.isBlank() || currentState.owner.isBlank()) {
                _state.value = currentState.copy(
                errorMessage = context.getString(R.string.error_message_complete_fields)
            )

            return
        }
        validateForm(context)
        if(_state.value.errorMessage.isNotEmpty()){
            return
        }
        viewModelScope.launch {
            try {
                // Mapea los datos ingresados a un modelo `Card` y llama al repositorio
                val card = Card(
                   fullName = currentState.owner,
                    cvv = currentState.cvv,
                    number = currentState.number,
                    expirationDate = currentState.exp,
                    type = currentState.type
                )
                walletRepository.addCard(card)


                _state.update { AddCardScreenState() }
                onAddCardSuccess?.invoke()
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        errorMessage = context.getString(R.string.error_message_add_card, e.message)
                    )
                }
            }
        }
    }

    fun onBankChange(newBank: String) {
        _state.update { it.copy(bank = newBank) }
       refreshError()
    }

    fun onNumberChange(newNumber: String) {
        _state.update { it.copy(number = newNumber) }
        refreshError()
    }

    fun onOwnerChange(newOwner: String) {
        _state.update { it.copy(owner = newOwner) }
        refreshError()
    }

    fun onCVVChange(newCVV: String) {
        _state.update { it.copy(cvv = newCVV) }
        refreshError()
    }

    fun onExpDateChange(newExp: String) {
        _state.update { it.copy(exp = newExp) }
        refreshError()
    }


    private fun validateForm(context: Context) {
        val card = CreditCard(
            bank = _state.value.bank,
            number = _state.value.number,
            owner = _state.value.owner,
            cvv = _state.value.cvv,
            exp = _state.value.exp
        )
       // val context = LocalContext.current
        val isValid = card.isValidCardNumber() && card.isValidCVV() && card.isValidExpDate()

        val errorMessage =
        when {
            !card.isValidCardNumber() -> context.getString(R.string.invalid_card_number)
            !card.isValidCVV() -> context.getString(R.string.invalid_cvv)
            !card.isValidExpDate() -> context.getString(R.string.invalid_exp_date)
            !card.dateNotExpired() -> context.getString(R.string.expired_card)
            else -> ""
        }

        _state.update { it.copy(isValid = isValid, errorMessage = errorMessage) }
    }

    private fun refreshError(){
        _state.update { it.copy( errorMessage = "") }
    }




    companion object {
        const val TAG = "UI Layer"

        fun provideFactory(
            app: MyApplication
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AddCardScreenViewModel(
                    app.walletRepository
                ) as T
            }
        }
    }
}

