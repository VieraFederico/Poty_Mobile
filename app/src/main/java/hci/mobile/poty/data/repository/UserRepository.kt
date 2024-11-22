package hci.mobile.poty.data.repository

import hci.mobile.poty.data.model.Code
import hci.mobile.poty.data.model.RegistrationUser
import hci.mobile.poty.data.model.Token
import hci.mobile.poty.data.model.User
import hci.mobile.poty.data.network.UserRemoteDataSource
import hci.mobile.poty.data.network.model.NetworkUser
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class UserRepository(
    private val remoteDataSource: UserRemoteDataSource
) {

    // Mutex to make writes to cached values thread-safe.
    private val currentUserMutex = Mutex()
    // Cache of the current user got from the network.
    private var currentUser: User? = null

    suspend fun login(username: String, password: String) {
        remoteDataSource.login(username, password)
    }

    suspend fun logout() {
        remoteDataSource.logout()
    }

    suspend fun getCurrentUser(refresh: Boolean) : User? {
        if (refresh || currentUser == null) {
            val result = remoteDataSource.getCurrentUser()
            // Thread-safe write to latestNews
            currentUserMutex.withLock {
                this.currentUser = result.asModel()
            }
        }

        return currentUserMutex.withLock { this.currentUser }
    }

    suspend fun register(user: RegistrationUser): User {
        return remoteDataSource.register(user.asNetworkModel()).asModel()
    }

    suspend fun verify(code: Code): User {
        return remoteDataSource.verify(code.asNetworkModel()).asModel()
    }

}