package hci.mobile.poty

import android.app.Application
import hci.mobile.poty.SessionManager
import hci.mobile.poty.data.network.UserRemoteDataSource
import hci.mobile.poty.data.network.WalletRemoteDataSource
import hci.mobile.poty.data.network.api.RetrofitClient
import hci.mobile.poty.data.repository.UserRepository
import hci.mobile.poty.data.repository.WalletRepository

class MyApplication : Application() {

    private val userRemoteDataSource: UserRemoteDataSource
        get() = UserRemoteDataSource(sessionManager, RetrofitClient.getUserApiService(this))

    private val walletRemoteDataSource: WalletRemoteDataSource
        get() = WalletRemoteDataSource(RetrofitClient.getWalletApiService(this))

    val sessionManager: SessionManager
        get() = SessionManager(this)

    val userRepository: UserRepository
        get() = UserRepository(userRemoteDataSource)

    val walletRepository: WalletRepository
        get() = WalletRepository(walletRemoteDataSource)
}