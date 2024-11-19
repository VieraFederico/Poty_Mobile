package hci.mobile.poty.api
import AddCardRequest
import AliasUpdateRequest
import BalanceResponse
import CardResponse
import DailyReturnResponse
import DeleteResponse
import DivestRequest
import InterestResponse
import InvestRequest
import InvestmentResponse
import LoginRequest
import LoginResponse
import PasswordRecoveryRequest
import PasswordResetRequest
import PaymentRequest
import PaymentResponse
import RechargeRequest
import RecoveryResponse
import ResetResponse
import SettleRequest
import SettleResponse
import UserRegistration
import UserResponse
import VerificationRequest
import WalletDetailsResponse
import retrofit2.Call
import retrofit2.http.*
import hci.mobile.poty.classes.*
interface WalletApi {
    // Payment endpoints
    @POST("api/payment")
    fun makePayment(@Body paymentRequest: PaymentRequest): Call<PaymentResponse>

    @GET("api/payment")
    fun getUserPayments(
        @Query("page") page: Int? = null,
        @Query("direction") direction: String? = null,
        @Query("pending") pending: Boolean? = null,
        @Query("type") type: String? = null,
        @Query("range") range: String? = null,
        @Query("source") source: String? = null,
        @Query("cardId") cardId: Int? = null
    ): Call<List<PaymentResponse>>

    @GET("api/payment/{paymentId}")
    fun getSpecificPayment(@Path("paymentId") paymentId: Int): Call<PaymentResponse>

    @GET("api/payment/link/{linkUuid}")
    fun getPaymentLinkDetails(@Path("linkUuid") linkUuid: String): Call<PaymentResponse>

    @POST("api/payment/link/{linkUuid}")
    fun settlePaymentLink(
        @Path("linkUuid") linkUuid: String,
        @Body settleRequest: SettleRequest
    ): Call<SettleResponse>

    // User endpoints
    @POST("api/user")
    fun registerUser(@Body userRegistration: UserRegistration): Call<UserResponse>

    @GET("api/user")
    fun getUserInfo(): Call<UserResponse>

    @POST("api/user/login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("api/user/verify")
    fun verifyUser(@Body verificationRequest: VerificationRequest): Call<UserResponse>

    @POST("api/user/recover-password")
    fun recoverPassword(@Body recoveryRequest: PasswordRecoveryRequest): Call<RecoveryResponse>

    @POST("api/user/reset-password")
    fun resetPassword(@Body resetRequest: PasswordResetRequest): Call<ResetResponse>

    @POST("api/user/logout")
    fun logoutUser(): Call<Void>

    // Wallet endpoints
    @GET("api/wallet/balance")
    fun getWalletBalance(): Call<BalanceResponse>

    @POST("api/wallet/recharge")
    fun rechargeWallet(@Body rechargeRequest: RechargeRequest): Call<BalanceResponse>

    @GET("api/wallet/investment")
    fun getInvestment(): Call<InvestmentResponse>

    @POST("api/wallet/invest")
    fun investWalletBalance(@Body investRequest: InvestRequest): Call<InvestmentResponse>

    @POST("api/wallet/divest")
    fun divestWalletBalance(@Body divestRequest: DivestRequest): Call<InvestmentResponse>

    @GET("api/wallet/cards")
    fun getUserCards(): Call<List<CardResponse>>

    @POST("api/wallet/cards")
    fun addCard(@Body addCardRequest: AddCardRequest): Call<CardResponse>

    @DELETE("api/wallet/cards/{cardId}")
    fun deleteCard(@Path("cardId") cardId: Int): Call<DeleteResponse>

    @GET("api/wallet/daily-returns")
    fun getDailyReturns(@Query("page") page: Int? = null): Call<List<DailyReturnResponse>>

    @GET("api/wallet/daily-interest")
    fun getDailyInterest(): Call<InterestResponse>

    @PUT("api/wallet/update-alias")
    fun updateWalletAlias(@Body aliasRequest: AliasUpdateRequest): Call<WalletDetailsResponse>

    @GET("api/wallet/details")
    fun getWalletDetails(): Call<WalletDetailsResponse>
}