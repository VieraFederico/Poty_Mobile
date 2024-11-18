package hci.mobile.poty.screens.investment

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import hci.mobile.poty.classes.Transaction
import hci.mobile.poty.classes.TransactionType
import hci.mobile.poty.classes.TransactionStatus
import java.time.LocalDateTime

class InvestmentScreenViewModel : ViewModel() {

    private val _state = MutableStateFlow(InvestmentScreenState())
    val state: StateFlow<InvestmentScreenState> = _state

    // Actualizar monto a invertir
    fun updateInvestAmount(amount: Float) {
        _state.value = _state.value.copy(investAmount = amount)
    }

    // Actualizar monto a retirar
    fun updateWithdrawAmount(amount: Float) {
        _state.value = _state.value.copy(withdrawAmount = amount)
    }

    // Establecer mensaje de error
    private fun setErrorMessage(message: String?) {
        _state.value = _state.value.copy(errorMessage = message)
    }

    // Realizar inversión
    fun invest(amount: Float) {
        when {
            amount <= 0 -> {
                setErrorMessage("El monto debe ser mayor a 0.")
            }
            amount > _state.value.balance -> {
                setErrorMessage("No tienes suficiente saldo disponible para esta inversión.")
            }
            else -> {
                // Actualizar balance e inversiones
                val newBalance = _state.value.balance - amount
                val newInvested = _state.value.invested + amount
                _state.value = _state.value.copy(
                    balance = newBalance,
                    invested = newInvested,
                    investAmount = 0f, // Reiniciar el campo
                    errorMessage = null // Limpiar errores
                )

                // Registrar la transacción
                val transaction = Transaction(
                    id = "${_state.value.transactions.size + 1}",
                    type = TransactionType.DEPOSIT,
                    amount = amount.toDouble(),
                    description = "Inversión realizada",
                    timestamp = LocalDateTime.now(),
                    status = TransactionStatus.COMPLETED
                )
                _state.value = _state.value.copy(
                    transactions = _state.value.transactions + transaction
                )
            }
        }
    }

    // Rescatar una inversión
    fun withdraw(amount: Float) {
        when {
            amount <= 0 -> {
                setErrorMessage("El monto debe ser mayor a 0.")
            }
            amount > _state.value.invested -> {
                setErrorMessage("No tienes suficiente saldo invertido para este retiro.")
            }
            else -> {
                // Actualizar balance e inversiones
                val newBalance = _state.value.balance + amount
                val newInvested = _state.value.invested - amount
                _state.value = _state.value.copy(
                    balance = newBalance,
                    invested = newInvested,
                    withdrawAmount = 0f, // Reiniciar el campo
                    errorMessage = null // Limpiar errores
                )

                // Registrar la transacción
                val transaction = Transaction(
                    id = "${_state.value.transactions.size + 1}",
                    type = TransactionType.WITHDRAWAL,
                    amount = amount.toDouble(),
                    description = "Retiro de inversión",
                    timestamp = LocalDateTime.now(),
                    status = TransactionStatus.COMPLETED
                )
                _state.value = _state.value.copy(
                    transactions = _state.value.transactions + transaction
                )
            }
        }
    }
}
