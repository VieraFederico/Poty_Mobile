package hci.mobile.poty.api
import retrofit2.Response

class ApiException(
    val response: Response<*>? = null,
    cause: Throwable? = null
) : Exception(response?.message(), cause)