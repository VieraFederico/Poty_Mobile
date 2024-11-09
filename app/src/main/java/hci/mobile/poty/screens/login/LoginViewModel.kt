package hci.mobile.poty.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hci.mobile.poty.classes.LoginForm
import hci.mobile.poty.classes.User
import hci.mobile.poty.screens.register.RegistrationEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.UpdateEmail -> updateEmail(event.email)
            is LoginEvent.UpdatePassword -> updatePassword(event.password)
            LoginEvent.Login -> validateAndLogin()
        }
    }

    private fun updateEmail(email: String) {
        _state.update { it.copy(email = email, errorMessage = "") }
    }

    private fun updatePassword(password: String) {
        _state.update { it.copy(password = password, errorMessage = "") }
    }

    // Valida los campos y realiza el login
    private fun validateAndLogin() {
        try {
            // Validación de los campos
            require(_state.value.email.isNotEmpty()) { "Por favor, ingrese un correo electrónico válido." }
            require(_state.value.password.isNotEmpty()) { "Por favor, ingrese una contraseña válida." }

            // Si pasa la validación, inicia sesión
            loginUser()
        } catch (e: IllegalArgumentException) {
            // Si hay un error en la validación, muestra el mensaje de error
            _state.value = _state.value.copy(errorMessage = e.message ?: "Error de validación")
        }
    }

    // Simula el proceso de login
    private fun loginUser() {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true) // Cambia el estado a cargando

                // Realizar acción de login (reemplazar con llamada a API real)
                // Supongamos que el login es exitoso:
                val user = LoginForm(email = _state.value.email, password = _state.value.password)

                // Llamada a la API para loguear al usuario

                _state.value = _state.value.copy(isLoading = false, errorMessage = "")

                // Aquí navegarías a la siguiente pantalla o mostrarías un mensaje de éxito

            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, errorMessage = e.message ?: "Error durante el inicio de sesión")
            }
        }
    }
}
