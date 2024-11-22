package hci.mobile.poty.data.network.api


import hci.mobile.poty.data.network.model.NetworkBalance
import hci.mobile.poty.data.network.model.NetworkCard
import hci.mobile.poty.data.network.model.NetworkRechargeRequest
import hci.mobile.poty.data.network.model.NetworkRechargeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface WalletApiService {

    @GET("wallet/cards")
    suspend fun getCards(): Response<List<NetworkCard>>

    @POST("wallet/cards")
    suspend fun addCard(@Body card: NetworkCard): Response<NetworkCard>

    @DELETE("wallet/cards/{cardId}")
    suspend fun deleteCard(@Path("cardId") cardId: Int): Response<Unit>

    @GET("wallet/balance")
    suspend fun getBalance() : Response<NetworkBalance>

    @POST("wallet/recharge")
    suspend fun recharge(@Body rechargeRequest: NetworkRechargeRequest) : Response<NetworkRechargeResponse>


}