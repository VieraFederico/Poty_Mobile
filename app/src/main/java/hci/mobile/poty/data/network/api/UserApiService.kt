package hci.mobile.poty.data.network.api

import hci.mobile.poty.data.network.model.NetworkCode
import hci.mobile.poty.data.network.model.NetworkCredentials
import hci.mobile.poty.data.network.model.NetworkRegistrationUser
import hci.mobile.poty.data.network.model.NetworkToken
import hci.mobile.poty.data.network.model.NetworkUser
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApiService {
    @POST("user/login")
    suspend fun login(@Body credentials: NetworkCredentials): Response<NetworkToken>

    @POST("user/logout")
    suspend fun logout(): Response<Unit>

    @GET("user")
    suspend fun getCurrentUser(): Response<NetworkUser>

    @POST("user")
    suspend fun register(@Body user: NetworkRegistrationUser): Response<NetworkUser>

    @POST("user/verify")
    suspend fun verify(@Body token: NetworkCode): Response<NetworkUser>

}