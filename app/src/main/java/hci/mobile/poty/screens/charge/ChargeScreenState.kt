import android.content.Context

data class ChargeScreenState(
    val currentStep: Int = 1,
    val amount: String = "",
    val generatedLink: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String = "",
)

sealed class ChargeScreenEvent {
    data class UpdateAmount(val amount: String) : ChargeScreenEvent()
    object NextStep : ChargeScreenEvent()
    object PreviousStep : ChargeScreenEvent()
    object GenerateNewLink : ChargeScreenEvent()
    data class ShareLink(val context: Context?) : ChargeScreenEvent()
    data class CopyLink(val context: Context?) : ChargeScreenEvent()
}
