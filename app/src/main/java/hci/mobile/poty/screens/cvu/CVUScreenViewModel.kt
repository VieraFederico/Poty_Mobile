package hci.mobile.poty.screens.cvu

import android.content.ClipData
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hci.mobile.poty.screens.dashboard.DashboardState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CVUViewModel : ViewModel() {
    private val _state = MutableStateFlow(CVUScreenState())
    val state: StateFlow<CVUScreenState> = _state.asStateFlow()

    fun fetchUserData() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            try {
                // Fetch de API para Wallet y User
                _state.update {
                    it.copy(
                        alias = "JamesBond007",
                        cbu = "1234567890",
                        email = "casinoroyale@gmail.com",
                        loading = false
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        error = "Failed to fetch user data",
                        loading = false
                    )
                }
            }
        }
    }
    fun copyToClipboard(context: Context, text: String) {
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
        val clipData = ClipData.newPlainText("Copied Text", text)
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(context, "Copied to clipboard!", Toast.LENGTH_SHORT).show()
    }

}