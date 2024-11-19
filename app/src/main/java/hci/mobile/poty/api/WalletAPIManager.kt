package hci.mobile.poty.api
import hci.mobile.poty.AppConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WalletApiManager(private val baseUrl: String = AppConfig.API_BASE_URL) {
    private var authToken: String? = null
    private val authRequestInterceptor = AuthorizationInterceptor()

    fun setAuthToken(newAuthToken: String?) {
        authToken = newAuthToken
        authRequestInterceptor.setAuthToken(authToken)
    }

    fun getAuthToken(): String? {
        return authToken
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authRequestInterceptor)
        .addInterceptor(RequestInterceptor)
        .build()

    private val retrofitClient: Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val walletApi: WalletApi = retrofitClient.create(WalletApi::class.java)

    private class AuthorizationInterceptor : Interceptor {
        private var authHeader: String? = null

        fun setAuthToken(newAuthToken: String?) {
            authHeader = newAuthToken?.let { "Bearer $it" }
        }

        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            val requestWithHeader = originalRequest.newBuilder().apply {
                authHeader?.let {
                    header("Authorization", it)
                }
            }.build()
            return chain.proceed(requestWithHeader)
        }
    }

    private object RequestInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            println("Request: ${request.method} to ${request.url} with headers ${request.headers}")
            return chain.proceed(request)
        }
    }
}