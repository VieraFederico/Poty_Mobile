package hci.mobile.poty.classes

class ChargeForm (
    val amount: String
){
    fun isValid(): Boolean {
        return amount.isNotEmpty() && amount.toDoubleOrNull() != null && amount.toDouble() > 0
    }
}