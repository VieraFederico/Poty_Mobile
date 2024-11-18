data class ChargeScreenState(
    val currentStep: ChargeStep = ChargeStep.AMOUNT,
    val amount: String = "",
    val isAmountValid: Boolean = false,
    val generatedLink: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

enum class ChargeStep {
    AMOUNT,
    LINK
}

sealed class ChargeScreenEvent {
    data class AmountChanged(val amount: String) : ChargeScreenEvent()
    object NextStepClicked : ChargeScreenEvent()
    object BackClicked : ChargeScreenEvent()
    object GenerateNewLink : ChargeScreenEvent()
    object ShareLink : ChargeScreenEvent()
    object CopyLink : ChargeScreenEvent()
}
