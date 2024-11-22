package hci.mobile.poty.data.model



import hci.mobile.poty.data.network.model.NetworkRegistrationUser
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class RegistrationUser(
    var firstName: String,
    var lastName: String,
    var email: String,
    var birthDate: Date,
    var password: String

) {
    fun asNetworkModel(): NetworkRegistrationUser {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault(Locale.Category.FORMAT))

        return NetworkRegistrationUser(
            firstName = firstName,
            lastName = lastName,
            email = email,
            birthDate = dateFormat.format(birthDate),
            password = password
        )
    }
}
