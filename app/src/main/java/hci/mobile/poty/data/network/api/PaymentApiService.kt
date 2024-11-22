package hci.mobile.poty.data.network.api



import hci.mobile.poty.data.network.model.NetworkBalancePayment
import hci.mobile.poty.data.network.model.NetworkCardPayment
import hci.mobile.poty.data.network.model.NetworkLinkPayment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PaymentApiService {
   @POST("payment")
   suspend fun payWithBalance(@Body balancePayment : NetworkBalancePayment): Response<Unit>

   @POST("payment")
   suspend fun payWithCard(@Body cardPayment : NetworkCardPayment): Response<Unit>

   @POST("payment")
   suspend fun payWithLink(@Body linkPayment: NetworkLinkPayment): Response<Unit>
}