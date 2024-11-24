package hci.mobile.poty.screens.alias

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import hci.mobile.poty.MyApplication
import kotlinx.coroutines.launch

class ChangeAliasViewModel(application: Application) : AndroidViewModel(application) {

    private val _state = MutableLiveData<ChangeAliasState>()
    val state: LiveData<ChangeAliasState> = _state

    init {
        _state.value = ChangeAliasState(alias = "")
    }

    fun updateAlias(newAlias: String) {
        _state.value = _state.value?.copy(alias = newAlias)
    }

    fun changeAlias() {
        if (_state.value?.alias?.isNotEmpty() == true) {
            // Assuming alias change is successful
            _state.value = _state.value?.copy(errorMessage = "")
        } else {
            _state.value = _state.value?.copy(errorMessage = "Alias cannot be empty")
        }
    }
}

data class ChangeAliasState(
    val alias: String = "",
    val errorMessage: String = ""
)
