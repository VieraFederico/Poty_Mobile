package hci.mobile.poty.data.network

import hci.mobile.poty.data.network.api.UserApiService
import hci.mobile.poty.data.network.model.NetworkCredentials
import hci.mobile.poty.data.network.model.NetworkUser
import hci.mobile.poty.SessionManager
import hci.mobile.poty.data.network.model.NetworkCode
import hci.mobile.poty.data.network.model.NetworkRegistrationUser
import hci.mobile.poty.data.network.model.NetworkToken

class UserRemoteDataSource(
    private val sessionManager: SessionManager,
    private val userApiService: UserApiService
) : RemoteDataSource() {

    suspend fun login(username: String, password: String) {
        val response = handleApiResponse {
            userApiService.login(NetworkCredentials(username, password))
        }
        sessionManager.saveAuthToken(response.token)
    }

    suspend fun logout() {
        handleApiResponse { userApiService.logout() }
        sessionManager.removeAuthToken()
    }

    suspend fun getCurrentUser(): NetworkUser {
        return handleApiResponse { userApiService.getCurrentUser() }
    }

    suspend fun register(user: NetworkRegistrationUser): NetworkUser {
        return handleApiResponse { userApiService.register(user) }
    }

    suspend fun verify(code: NetworkCode): NetworkUser {
        return handleApiResponse { userApiService.verify(code) }
    }

}