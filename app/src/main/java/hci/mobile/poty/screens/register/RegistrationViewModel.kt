package hci.mobile.poty.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hci.mobile.poty.classes.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


// RegistrationViewModel.kt
class RegistrationViewModel : ViewModel() {
    private val _state = MutableStateFlow(RegistrationState())
    val state = _state.asStateFlow()

    fun onEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.UpdateName -> updateName(event.name)
            is RegistrationEvent.UpdateSurname -> updateSurname(event.surname)
            is RegistrationEvent.UpdateEmail -> updateEmail(event.email)
            is RegistrationEvent.UpdatePassword -> updatePassword(event.password)
            is RegistrationEvent.UpdateBirthday -> updateBirthday(event.birthday)
            is RegistrationEvent.UpdateGender -> updateGender(event.gender)
            is RegistrationEvent.UpdateCountry -> updateCountry(event.country)
            is RegistrationEvent.UpdateCity -> updateCity(event.city)
            is RegistrationEvent.UpdateConfirmationCode -> UpdateConfirmationCode(event.confirmationCode)
            RegistrationEvent.NextStep -> validateAndMoveToNextStep()
            RegistrationEvent.PreviousStep -> moveToPreviousStep()
            RegistrationEvent.Submit -> submitRegistration()
        }
    }

    private fun updateName(name: String) {
        _state.update { it.copy(name = name, errorMessage = "") }
    }

    private fun updateSurname(surname: String) {
        _state.update { it.copy(surname = surname, errorMessage = "") }
    }

    private fun updateEmail(email: String) {
        _state.update { it.copy(email = email, errorMessage = "") }
    }

    private fun updatePassword(password: String) {
        _state.update { it.copy(password = password, errorMessage = "") }
    }

    private fun updateBirthday(birthday: String) {
        _state.update { it.copy(birthday = birthday, errorMessage = "") }
    }

    private fun updateGender(gender: String) {
        _state.update { it.copy(gender = gender, errorMessage = "") }
    }

    private fun updateCountry(country: String) {
        _state.update { it.copy(country = country, errorMessage = "") }
    }

    private fun updateCity(city: String) {
        _state.update { it.copy(city = city, errorMessage = "") }
    }

    private fun UpdateConfirmationCode(confirmationCode: String) {
        _state.update { it.copy(confirmationCode = confirmationCode, errorMessage = "") }
    }

    private fun validateAndMoveToNextStep() {
        when (_state.value.currentStep) {
            1 -> validateStepOne()
            2 -> validateStepTwo()
            3 -> validateStepThree()
        }
    }

    private fun validateStepOne() {
        try {
            with(_state.value) {
                require(name.isNotEmpty()) { "Por favor, ingrese un nombre válido." }
                require(surname.isNotEmpty()) { "Por favor, ingrese un apellido válido." }
                require(birthday.isNotEmpty()) { "Por favor, ingrese una fecha de nacimiento válida." }
            }
            _state.update { it.copy(currentStep = 2, errorMessage = "") }
        } catch (e: IllegalArgumentException) {
            _state.update { it.copy(errorMessage = e.message ?: "Error de validación") }
        }
    }


    private fun validateStepTwo() {
        try {
            with(_state.value) {
                require(gender.isNotEmpty()) { "Por favor, seleccione un género." }
                require(country.isNotEmpty()) { "Por favor, ingrese un país." }
                require(city.isNotEmpty()) { "Por favor, ingrese una ciudad." }
            }
            submitRegistration()
        } catch (e: IllegalArgumentException) {
            _state.update { it.copy(errorMessage = e.message ?: "Error de validación") }
        }
    }

    private fun validateStepThree() {
        try {
            with(_state.value) {
                require(email.isNotEmpty()) { "Por favor, ingrese un correo electrónico válido." }
                require(password.isNotEmpty()) { "Por favor, ingrese una contraseña válida." }
            }
            _state.update { it.copy(currentStep = 3, errorMessage = "") }
        } catch (e: IllegalArgumentException) {
            _state.update { it.copy(errorMessage = e.message ?: "Error de validación") }
        }
    }

    private fun moveToPreviousStep() {
        if (_state.value.currentStep > 1) {
            _state.update { it.copy(currentStep = it.currentStep - 1, errorMessage = "") }
        }
    }

    private fun submitRegistration() {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true) }

                val user = User(
                    name = _state.value.name,
                    surname = _state.value.surname,
                    email = _state.value.email,
                    birthday = _state.value.birthday,
                    gender = _state.value.gender,
                    country = _state.value.country,
                    city = _state.value.city
                )

                // Aquí irían las llamadas a tu API o repositorio
                // userRepository.register(user)

                _state.update { it.copy(isLoading = false, errorMessage = "") }
                // Navegar a la siguiente pantalla o mostrar éxito

            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Error durante el registro"
                    )
                }
            }
        }
    }
}