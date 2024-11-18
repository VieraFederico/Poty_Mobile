package hci.mobile.poty.classes

import java.util.regex.Pattern

class User(
    val name: String,
    val surname: String,
    val email: String,
    val birthday: String,
    /*val gender: String,
    val country: String,
    val city: String,*/
) {
    init {
        validateName(name)
        validateSurname(surname)
        validateEmail(email)
        validateBirthday(birthday)
        /*validateGender(gender)
        validateCountry(country)
        validateCity(city)*/
    }

    // Validación de nombre: solo caracteres alfabéticos y no vacío
    private fun validateName(name: String) {
        require(name.isNotEmpty()) { "El nombre no puede estar vacío" }
        require(name.all { it.isLetter() || it.isWhitespace() }) { "El nombre debe contener solo letras y espacios" }
    }

    // Validación de apellido: solo caracteres alfabéticos y no vacío
    private fun validateSurname(surname: String) {
        require(surname.isNotEmpty()) { "El apellido no puede estar vacío" }
        require(surname.all { it.isLetter() || it.isWhitespace() }) { "El apellido debe contener solo letras y espacios" }
    }

    // Validación de correo electrónico: verificar el formato básico usando regex
    private fun validateEmail(email: String) {
        val emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
        val pattern = Pattern.compile(emailPattern)
        require(pattern.matcher(email).matches()) { "El formato del correo electrónico no es válido" }
    }

    private fun validateBirthday(birthday: String) {
        val datePattern = "^\\d{2}/\\d{2}/\\d{4}$"
        val pattern = Pattern.compile(datePattern)
        require(pattern.matcher(birthday).matches()) {
            "La fecha de nacimiento debe estar en el formato DD/MM/YYYY"
        }
    }

/*
    // Validación de género: asegurarse de que el género sea "Masculino", "Femenino" u "Otro"
    private fun validateGender(gender: String) {
        val validGenders = setOf("Masculino", "Femenino", "Otro")
        require(gender in validGenders) { "El género debe ser 'Masculino', 'Femenino' u 'Otro'" }
    }

    // Validación de país: asegurarse de que no esté vacío y contenga solo letras y espacios
    private fun validateCountry(country: String) {
        require(country.isNotEmpty()) { "El país no puede estar vacío" }
        require(country.all { it.isLetter() || it.isWhitespace() }) { "El país debe contener solo letras y espacios" }
    }

    // Validación de ciudad: asegurarse de que no esté vacío y contenga solo letras y espacios
    private fun validateCity(city: String) {
        require(city.isNotEmpty()) { "La ciudad no puede estar vacía" }
        require(city.all { it.isLetter() || it.isWhitespace() }) { "La ciudad debe contener solo letras y espacios" }
    }*/
}
