package hci.mobile.poty.screens.settings

import android.content.ClipData
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import hci.mobile.poty.MyApplication
import hci.mobile.poty.data.repository.UserRepository
import hci.mobile.poty.data.repository.WalletRepository
import hci.mobile.poty.screens.addCard.AddCardScreenViewModel
import hci.mobile.poty.screens.cvu.CVUScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel (
    private val userRepository: UserRepository,

    ): ViewModel() {
    private val _state = MutableStateFlow(SettingsScreenState())
    val state: StateFlow<SettingsScreenState> = _state.asStateFlow()

    fun onLogOut(onNavigateToLanding: () -> Unit) {
        viewModelScope.launch {
            try {
                userRepository.logout()
                onNavigateToLanding()
            } catch (e: Exception) {
            }
        }
    }

    companion object {
        const val TAG = "UI Layer"

        fun provideFactory(
            app: MyApplication
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SettingsViewModel(
                    app.userRepository
                ) as T
            }
        }
    }
}
