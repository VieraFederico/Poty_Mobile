package hci.mobile.poty.screens.register


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MockRegistrationViewModel : ViewModel() {
    private val _state = MutableStateFlow(RegistrationState())
    val state: StateFlow<RegistrationState> = _state
    private val _isRegistrationSuccessful = MutableStateFlow(false)
    val isRegistrationSuccessful: StateFlow<Boolean> = _isRegistrationSuccessful


    fun onEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.UpdateName -> {
                _state.update { it.copy(name = event.name) }
            }
            is RegistrationEvent.UpdateSurname -> {
                _state.update { it.copy(surname = event.surname) }
            }
            is RegistrationEvent.UpdateEmail -> {
                _state.update { it.copy(email = event.email) }
            }
            is RegistrationEvent.UpdatePassword -> {
                _state.update { it.copy(password = event.password) }
            }
            is RegistrationEvent.UpdateBirthday -> {
                _state.update { it.copy(birthday = event.birthday) }
            }
            is RegistrationEvent.UpdateConfirmationCode -> {
                _state.update { it.copy(confirmationCode = event.confirmationCode) }
            }
            RegistrationEvent.NextStep -> {
                _state.update { it.copy(currentStep = it.currentStep + 1) }
            }
            RegistrationEvent.PreviousStep -> {
                _state.update { it.copy(currentStep = it.currentStep - 1) }
            }
            RegistrationEvent.Submit -> {
                _state.update { it.copy(isLoading = true) }
                viewModelScope.launch {
                    _isRegistrationSuccessful.value = true
                    _state.update { it.copy(isLoading = false) }
                }
            }
            RegistrationEvent.verifyCode -> {
                _state.update { it.copy(isLoading = true) }
                viewModelScope.launch {
                    _isRegistrationSuccessful.value = true
                    _state.update { it.copy(isLoading = false) }
                }
            }
        }
    }
}
