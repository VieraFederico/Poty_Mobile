package hci.mobile.poty.screens.register

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import hci.mobile.poty.MyApplication
import hci.mobile.poty.data.model.Code
import hci.mobile.poty.data.model.RegistrationUser
import hci.mobile.poty.data.model.User
import hci.mobile.poty.data.repository.UserRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class RegistrationViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    var state by mutableStateOf(RegistrationState())
        private set

    var isRegistrationSuccessful by mutableStateOf(false)
        private set

     fun onEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.UpdateName -> updateName(event.name)
            is RegistrationEvent.UpdateSurname -> updateSurname(event.surname)
            is RegistrationEvent.UpdateEmail -> updateEmail(event.email)
            is RegistrationEvent.UpdatePassword -> updatePassword(event.password)
            is RegistrationEvent.UpdateBirthday -> updateBirthday(event.birthday)
            is RegistrationEvent.UpdateConfirmationCode -> updateConfirmationCode(event.confirmationCode)
            RegistrationEvent.NextStep -> validateAndMoveToNextStep()
            RegistrationEvent.NextSubStep -> validateAndMoveToNextSubStep()
            RegistrationEvent.PreviousStep -> moveToPreviousStep()
            RegistrationEvent.PreviousSubStep -> moveToPreviousSubStep()
            RegistrationEvent.Submit -> submitRegistration()
            RegistrationEvent.verifyCode -> verify()
        }
    }

    private fun updateName(name: String) {
        state = state.copy(name = name, errorMessage = "")
    }

    private fun updateSurname(surname: String) {
        state = state.copy(surname = surname, errorMessage = "")
    }

    private fun updateEmail(email: String) {
        state = state.copy(email = email, errorMessage = "")
    }

    private fun updatePassword(password: String) {
        state = state.copy(password = password, errorMessage = "")
    }

    private fun updateBirthday(birthday: String) {
        state = state.copy(birthday = birthday, errorMessage = "")
    }

    private fun updateConfirmationCode(confirmationCode: String) {
        state = state.copy(confirmationCode = confirmationCode, errorMessage = "")
    }

    private fun validateAndMoveToNextSubStep(){
        if(state.currentSubStep == 0){
            when (state.currentStep) {
                1 -> validateSubStepOneStepZero()
                2 -> validateSubStepTwo()
                3 -> validateStepThree()
            }
            return;
        }else{
            when (state.currentStep) {
                1 -> validateSubStepOneStepOne()
                2 -> validateSubStepTwo()
                3 -> validateStepThree()
            }
        }

    }

    private fun validateAndMoveToNextStep() {
        when (state.currentStep) {
            1 -> validateStepOne()
            2 -> validateStepTwo()
            3 -> validateStepThree()
        }
    }




    private fun validateStepOne() {
        try {
            with(state) {
                require(name.isNotEmpty()) { "Por favor, ingrese un nombre válido." }
                require(surname.isNotEmpty()) { "Por favor, ingrese un apellido válido." }
                require(birthday.isNotEmpty()) { "Por favor, ingrese una fecha de nacimiento válida." }
                require(isValidDate(birthday)) { "Por favor, ingrese una fecha de nacimiento válida en formato DD/MM/AAAA." }
                require(!isFutureDate(birthday)) { "Por favor, ingresar una fecha anterior o igual a la actual." }
            }
            state = state.copy(currentStep = 2, errorMessage = "", currentSubStep = 0)
        } catch (e: IllegalArgumentException) {
            state = state.copy(errorMessage = e.message ?: "Error de validación")
        }
    }

    private fun validateSubStepOneStepZero() {
        try {
            with(state) {
                require(name.isNotEmpty()) { "Por favor, ingrese un nombre válido." }
                require(surname.isNotEmpty()) { "Por favor, ingrese un apellido válido." }
            }
            state = state.copy(currentSubStep = 1, errorMessage = "")
        } catch (e: IllegalArgumentException) {
            state = state.copy(errorMessage = e.message ?: "Error de validación")
        }
    }
    private fun validateSubStepTwo(){
        try {
            with(state) {
                require(password.isNotEmpty()) { "Por favor, ingrese una contraseña válida." }
                require(password.length >= 6) { "Por favor, la contraseña debe tener más de 6 dígitos." }            }
            submitRegistration()
            state = state.copy(currentStep = 3, errorMessage = "", currentSubStep = 0)
        } catch (e: IllegalArgumentException) {
            state = state.copy(errorMessage = e.message ?: "Error de validación")
        }
    }

    private fun validateSubStepOneStepOne() {
        try {
            with(state) {
                require(birthday.isNotEmpty()) { "Por favor, ingrese una fecha de nacimiento válida." }
                require(isValidDate(birthday)) { "Por favor, ingrese una fecha de nacimiento válida en formato DD/MM/AAAA." }
                require(!isFutureDate(birthday)) { "Por favor, ingresar una fecha anterior o igual a la actual." }
                require(email.isNotEmpty()) { "Por favor, ingrese un correo electrónico válido." }
                require(isValidEmail(email)) { "Por favor, ingrese un correo electrónico con formato válido. " }
            }
            state = state.copy(currentSubStep = 0, errorMessage = "", currentStep = 2)
        } catch (e: IllegalArgumentException) {
            state = state.copy(errorMessage = e.message ?: "Error de validación")
        }
    }




    private fun isFutureDate(date: String): Boolean {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return try {
            val parsedDate = LocalDate.parse(date, formatter)
            parsedDate.isAfter(LocalDate.now())
        } catch (e: Exception) {
            false
        }
    }

private fun isValidDate(date: String): Boolean {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return try {
        val parts = date.split("/")
        if (parts.size != 3) return false

        val day = parts[0].toIntOrNull() ?: return false
        val month = parts[1].toIntOrNull() ?: return false
        val year = parts[2].toIntOrNull() ?: return false

        if (parts[0].length != 2 || parts[1].length != 2 || parts[2].length != 4) return false

        val parsedDate = LocalDate.of(year, month, day)
        true
    } catch (e: Exception) {
        false
    }
}



    private fun validateStepTwo() {
        try {
            with(state) {
                require(email.isNotEmpty()) { "Por favor, ingrese un correo electrónico válido." }
                require(isValidEmail(email)) { "Por favor, ingrese un correo electrónico con formato válido. " }
                require(password.isNotEmpty()) { "Por favor, ingrese una contraseña válida." }
                require(password.length >= 6) { "Por favor, la contraseña debe tener más de 6 dígitos." }
            }
            submitRegistration()
            state = state.copy(currentStep = 3, errorMessage = "", currentSubStep = 0)
        } catch (e: IllegalArgumentException) {
            state = state.copy(errorMessage = e.message ?: "Error de validación")
        }
    }

    private fun validateStepThree() {

            try {
                with(state) {
                    require(confirmationCode.isNotEmpty()) { "Por favor, ingrese el código de confirmación." }
                    require(confirmationCode.length == 16) { "El código de confirmación debe tener 16 dígitos." }
                }
                viewModelScope.launch {
                    userRepository.verify(Code(state.confirmationCode))
                }

            } catch (e: IllegalArgumentException) {
                state = state.copy(errorMessage = e.message ?: "Error de validación")
            }

    }

    private fun verify(){
        viewModelScope.launch {
            try {
                with(state) {
                    require(confirmationCode.isNotEmpty()) { "Por favor, ingrese el código de confirmación." }
                    require(confirmationCode.length == 16) { "El código de confirmación debe tener 16 dígitos." }
                }
                val code = Code(state.confirmationCode)
                userRepository.verify(code)
                isRegistrationSuccessful = true // Marca la verificación como exitosa
            } catch (e: Exception) {
                state = state.copy(
                    errorMessage = e.message ?: "Error al verificar el código"
                )
                isRegistrationSuccessful = false // Asegúrate de no marcarlo como exitoso
            }
    }


    }

    private fun moveToPreviousStep() {
        if (state.currentStep > 1) {
            state = state.copy(currentStep = state.currentStep - 1, errorMessage = "", currentSubStep = 1)
        }
    }
    private fun moveToPreviousSubStep() {
        if (state.currentSubStep == 1) {
            state = state.copy(currentSubStep = 0, errorMessage = "")
        }else if(state.currentStep > 1){
            state = state.copy(currentStep =  state.currentStep - 1, errorMessage = "", currentSubStep = 1)
        }
    }


    @SuppressLint("SimpleDateFormat")
    private fun submitRegistration() {
        viewModelScope.launch {
            try {
                state = state.copy(isLoading = true)

                val user = RegistrationUser(
                    firstName = state.name,
                    lastName = state.surname,
                    email = state.email,
                    birthDate = SimpleDateFormat("dd/mm/yyyy", Locale.getDefault()).parse(state.birthday)!!,
                    password = state.password
                )

                userRepository.register(user)

                state = state.copy(isLoading = false, errorMessage = "")


            } catch (e: Exception) {
                state = state.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Ocurrió un error inesperado"
                )
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
                return RegistrationViewModel(
                    app.userRepository
                ) as T
            }
        }
    }
}

private fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    return email.matches(emailRegex.toRegex())
}


