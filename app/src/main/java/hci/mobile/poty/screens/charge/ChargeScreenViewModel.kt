import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class ChargeScreenViewModel : ViewModel() {

    private val _state = MutableStateFlow(ChargeScreenState())
    val state = _state.asStateFlow()

    fun onEvent(event: ChargeScreenEvent) {
        when {
            event is ChargeScreenEvent.AmountChanged -> updateAmount(event.amount)
            event == ChargeScreenEvent.NextStepClicked -> handleNextStep()
            event == ChargeScreenEvent.BackClicked -> handleBack()
            event == ChargeScreenEvent.GenerateNewLink -> generatePaymentLink()
            event == ChargeScreenEvent.ShareLink -> shareLink()
            event == ChargeScreenEvent.CopyLink -> copyLinkToClipboard()
            
        }
    }

    private fun updateAmount(newAmount: String) {
        val isValid = newAmount.toFloatOrNull()?.let { it > 0 } == true // Validar si es un float válido y positivo
        _state.update {
            it.copy(
                amount = newAmount,
                isAmountValid = isValid
            )
        }
    }


    private fun handleNextStep() {
        if (_state.value.isAmountValid) {
            generatePaymentLink()
        } else {
            _state.update {
                it.copy(error = "El monto ingresado no es válido.")
            }
        }
    }

    private fun handleBack() {
        _state.update { currentState ->
            when (currentState.currentStep) {
                ChargeStep.LINK -> currentState.copy(
                    currentStep = ChargeStep.AMOUNT,
                    generatedLink = ""
                )
                ChargeStep.AMOUNT -> currentState // No action needed
            }
        }
    }

    private fun generatePaymentLink() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val generatedLink = "poty.app/pay/${UUID.randomUUID()}"
                _state.update {
                    it.copy(
                        currentStep = ChargeStep.LINK,
                        generatedLink = generatedLink,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(isLoading = false, error = "Error al generar el enlace.")
                }
            }
        }
    }

    private fun shareLink() {
        // Implement share logic
    }

    private fun copyLinkToClipboard() {
        // Implement clipboard copy logic
    }
}
