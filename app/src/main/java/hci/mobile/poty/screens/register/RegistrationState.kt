package hci.mobile.poty.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hci.mobile.poty.classes.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RegistrationState(
    val name: String = "",
    val surname: String = "",
    val email: String = "",
    val password: String = "",
    val birthday: String = "",
    val gender: String = "",
    val country: String = "",
    val city: String = "",
    val errorMessage: String = "",
    val currentStep: Int = 1,
    val isLoading: Boolean = false,
    val confirmationCode: String = ""
)

sealed class RegistrationEvent {
    data class UpdateName(val name: String) : RegistrationEvent()
    data class UpdateSurname(val surname: String) : RegistrationEvent()
    data class UpdateEmail(val email: String) : RegistrationEvent()
    data class UpdatePassword(val password: String) : RegistrationEvent()
    data class UpdateBirthday(val birthday: String) : RegistrationEvent()
    data class UpdateGender(val gender: String) : RegistrationEvent()
    data class UpdateCountry(val country: String) : RegistrationEvent()
    data class UpdateCity(val city: String) : RegistrationEvent()
    data class UpdateConfirmationCode(val confirmationCode: String) : RegistrationEvent()
    object NextStep : RegistrationEvent()
    object PreviousStep : RegistrationEvent()
    object Submit : RegistrationEvent()
}
