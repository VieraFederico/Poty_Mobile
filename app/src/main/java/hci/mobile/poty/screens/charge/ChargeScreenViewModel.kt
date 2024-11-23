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
import hci.mobile.poty.R

class ChargeScreenViewModel (context: Context): ViewModel() {

    private val _state = MutableStateFlow(ChargeScreenState())
    val state = _state.asStateFlow()
    val context by lazy { context }
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
                require(amount.isNotEmpty() && amount.toDouble() > 0) { context.getString(R.string.error_invalid_amount)}
            }
            _state.update { it.copy(currentStep = 2, errorMessage = "") }
        } catch (e: IllegalArgumentException) {
            _state.update {
                it.copy(errorMessage = e.message ?: context.getString(R.string.validation_error))
            }
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
                require(amount.isNotEmpty() && amount.toDouble() > 0) { context.getString(R.string.error_invalid_amount) }

                val generatedLink = "poty.app/pay/${UUID.randomUUID()}?amount=$amount"

                _state.update {
                    it.copy(
                        generatedLink = generatedLink,
                        isLoading = false,
                        errorMessage = ""
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = context.getString(R.string.error_generating_link, e.message ?: "")
                    )
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
            val shareIntent = Intent.createChooser(intent, context.getString(R.string.share_link))
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(shareIntent)
        } else {
            _state.update {
                it.copy(errorMessage = context.getString(R.string.no_link_to_share))
            }
        }
    }

    fun copyLinkToClipboard(context: Context) {
        val link = _state.value.generatedLink
        if (link.isNotEmpty()) {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
            val clip = ClipData.newPlainText("payment_link", link)
            clipboard?.setPrimaryClip(clip)
            _state.update { it.copy(errorMessage = "") }
        } else {
            _state.update { it.copy(errorMessage = context.getString(R.string.no_link_to_copy)) }
        }
    }
}
