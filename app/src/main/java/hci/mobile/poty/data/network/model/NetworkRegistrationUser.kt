package hci.mobile.poty.data.network.model


import kotlinx.serialization.Serializable


@Serializable
class NetworkRegistrationUser(
    var firstName: String,
    var lastName: String,
    var email: String,
    var birthDate: String,
    var password: String

)