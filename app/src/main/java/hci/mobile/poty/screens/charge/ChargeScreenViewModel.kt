import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import android.content.Intent
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import hci.mobile.poty.MyApplication
import hci.mobile.poty.data.model.NewPaymentLink
import hci.mobile.poty.data.repository.PaymentRepository
import hci.mobile.poty.screens.addCard.AddCardScreenViewModel

class ChargeScreenViewModel(
    private val paymentRepository: PaymentRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ChargeScreenState())
    val state = _state.asStateFlow()

    fun onEvent(event: ChargeScreenEvent) {
        when(event){
            is ChargeScreenEvent.UpdateAmount -> updateAmount(event.amount)
            is ChargeScreenEvent.GenerateNewLink -> generatePaymentLink()
            is ChargeScreenEvent.ShareLink -> event.context?.let { shareLink(it) }
            is ChargeScreenEvent.CopyLink -> event.context?.let { copyLinkToClipboard(it) }
            ChargeScreenEvent.NextStep -> validateAndMoveToNextStep()
            ChargeScreenEvent.PreviousStep -> moveToPreviousStep()
            else -> {}
        }
    }

    private fun validateAndMoveToNextStep() {
        try{
            with(_state.value){
                require(amount.isNotEmpty() && amount.toDouble() > 0) {"Por favor, ingrese un monto válido."}
            }
            _state.update { it.copy(currentStep = 2, errorMessage = "") }
        } catch (e: IllegalArgumentException) {
            _state.update { it.copy(errorMessage = e.message ?: "Error de validación") }
        }
    }

    private fun moveToPreviousStep() {
        if (_state.value.currentStep > 1) {
            _state.update { it.copy(currentStep = it.currentStep - 1, errorMessage = "") }
        }
    }

    private fun updateAmount(newAmount: String) {
        val isValid = newAmount.toFloatOrNull()?.let { it > 0 } == true
        _state.update {
            it.copy(
                amount = newAmount,
            )
        }
    }


    private fun generatePaymentLink() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val amount = _state.value.amount
                require(amount.isNotEmpty() && amount.toDouble() > 0) { "Por favor, ingrese un monto válido." }

                val generatedLink = paymentRepository.generateLink(
                    linkPayment = NewPaymentLink(
                        amount = amount.toFloat(),
                        description = "t",
                        type = "LINK"
                    )
                )

                _state.update {
                    it.copy(
                        generatedLink = generatedLink.linkUuid,
                        isLoading = false,
                        errorMessage = ""
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(isLoading = false, errorMessage = "Error al generar el enlace. ${e.message}")
                }
            }
        }
    }


    fun shareLink(context: Context) {
        val link = _state.value.generatedLink
        if (link.isNotEmpty()) {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, link)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(intent, "Compartir enlace")
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(shareIntent)
        } else {
            _state.update { it.copy(errorMessage = "No hay enlace para compartir") }
        }
    }

    fun copyLinkToClipboard(context: Context) {
        val link = _state.value.generatedLink
        if (link.isNotEmpty()) {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
            val clip = ClipData.newPlainText("payment_link", link)
            clipboard?.setPrimaryClip(clip)
            _state.update { it.copy(errorMessage = "Enlace copiado al portapapeles") }
        } else {
            _state.update { it.copy(errorMessage = "No hay enlace para copiar") }
        }
    }

    companion object {
        const val TAG = "UI Layer"

        fun provideFactory(
            app: MyApplication
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ChargeScreenViewModel(
                    app.paymentRepository
                ) as T
            }
        }
    }
}
