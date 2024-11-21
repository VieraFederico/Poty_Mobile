package hci.mobile.poty.data.network

import android.util.Log
import hci.mobile.poty.data.DataSourceException
import hci.mobile.poty.data.network.model.NetworkError
import kotlinx.serialization.json.Json
import retrofit2.Response
import java.io.IOException

abstract class RemoteDataSource {
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun <T : Any> handleApiResponse(
        execute: suspend () -> Response<T>
    ): T {
        try {
            val response = execute()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                return body
            }
            response.errorBody()?.let {
                response.code()
                val error = json.decodeFromString<NetworkError>(it.string())
                throwDataSourceException(response.code(), error.message)
            }
            throw DataSourceException(UNEXPECTED_ERROR_CODE, "Missing error")
        } catch (e: DataSourceException) {
            throw e
        } catch (e: IOException) {
            throw DataSourceException(
                CONNECTION_ERROR_CODE,
                "Connection error"
            )
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected error handling API response", e)
            throw DataSourceException(
                UNEXPECTED_ERROR_CODE,
                "Unexpected error"
            )
        }
    }

    private fun throwDataSourceException(statusCode: Int, message: String): DataSourceException {
        when (statusCode) {
            400 -> throw DataSourceException(DATA_ERROR, message)
            401 -> throw DataSourceException(UNAUTHORIZED_ERROR_CODE, message)
            else -> throw DataSourceException(UNEXPECTED_ERROR_CODE, message)
        }
    }

    companion object {
        const val TAG = "Data Layer"

        const val UNAUTHORIZED_ERROR_CODE = 1
        const val DATA_ERROR = 2
        // TODO
        const val CONNECTION_ERROR_CODE = 98
        const val UNEXPECTED_ERROR_CODE = 99
    }
}