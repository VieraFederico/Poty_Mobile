package hci.mobile.poty.classes

import java.util.Calendar
import java.util.UUID
import java.util.regex.Pattern

data class CreditCard(
    val id: String = UUID.randomUUID().toString(),
    val bank: String,
    val number: String,
    val owner: String,
    val cvv: String,
    val exp: String,
){
    // Mask the credit card number to only show the last four digits
    fun maskedCardNumber(): String {
        return "**** **** **** ${number.takeLast(4)}"
    }

    // Mask the CVV number
    fun maskedCVV(): String {
        return "****"
    }

    // Validate the credit card number (basic validation, can be expanded)
    fun isValidCardNumber(): Boolean {
        // Example: simple check for 16 digits
        return number.length == 16 && number.all { it.isDigit() }
    }

    // Validate CVV (basic check for 3 digits)
    fun isValidCVV(): Boolean {
        return cvv.length == 3 && cvv.all { it.isDigit() }
    }

    // Validate expiration date (MM/YY format)
    fun isValidExpDate(): Boolean {
        val pattern = Pattern.compile("(0[1-9]|1[0-2])/([0-9]{2})")
        return pattern.matcher(exp).matches()
    }
    fun dateNotExpired(): Boolean {
        val parts = exp.split("/")
        if (parts.size != 2) return false

        val month = parts[0].toIntOrNull() ?: return false
        val year = parts[1].toIntOrNull() ?: return false

        val currentYear = Calendar.getInstance().get(Calendar.YEAR) % 100
        val currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1

        return (year > currentYear) || (year == currentYear && month >= currentMonth)
    }
}

