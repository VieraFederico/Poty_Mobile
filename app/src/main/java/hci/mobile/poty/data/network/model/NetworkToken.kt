package hci.mobile.poty.data.network.model

import hci.mobile.poty.data.model.User
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Locale

@Serializable
data class NetworkToken(

    var token: String
)