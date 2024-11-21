package hci.mobile.poty.data.model

import hci.mobile.poty.data.network.model.NetworkCard
import hci.mobile.poty.data.network.model.NetworkUser
import hci.mobile.poty.screens.login.LoginEvent
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class User(
    var id: Int?,
    var firstName: String,
    var lastName: String,
    var email: String,
    var birthDate: Date,
    var password: String?
) {
    fun asNetworkModel(): NetworkUser {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault(Locale.Category.FORMAT))

        return NetworkUser(
            id = id,
            firstName = firstName,
            lastName = lastName,
            email = email,
            birthDate = dateFormat.format(birthDate),
            password = password
        )
    }
}
