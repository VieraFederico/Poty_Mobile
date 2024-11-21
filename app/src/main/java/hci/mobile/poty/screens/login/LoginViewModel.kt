package hci.mobile.poty.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import hci.mobile.poty.MyApplication
import hci.mobile.poty.classes.LoginForm
import hci.mobile.poty.classes.User
import hci.mobile.poty.data.repository.UserRepository
import hci.mobile.poty.screens.register.RegistrationEvent
import hci.mobile.poty.screens.register.RegistrationViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    var onLoginSuccess: (() -> Unit)? = null // Callback para navegar al Dashboard

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.UpdateEmail -> updateEmail(event.email)
            is LoginEvent.UpdatePassword -> updatePassword(event.password)
            LoginEvent.Login -> validateAndLogin()
        }
    }

    fun updateEmail(email: String) {
        _state.update { it.copy(email = email, errorMessage = "") }
    }

    fun updatePassword(password: String) {
        _state.update { it.copy(password = password, errorMessage = "") }
    }

    // Valida los campos y realiza el login
    fun validateAndLogin() {
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

    private fun loginUser() {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true) // Cambia el estado a cargando


                userRepository.login(_state.value.email, _state.value.password)

                _state.value = _state.value.copy(isLoading = false, errorMessage = "")

                onLoginSuccess?.invoke()

            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, errorMessage = e.message ?: "Error durante el inicio de sesión")
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
                return LoginViewModel(
                    app.userRepository
                ) as T
            }
        }
    }
}
