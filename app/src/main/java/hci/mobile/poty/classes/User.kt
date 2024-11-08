package hci.mobile.poty.classes

import java.util.regex.Pattern

class User(
    val name: String,
    val surname: String,
    val email: String,
    val birthday: String,
    val gender: String,
    val country: String,
    val city: String,
) {
    init {
        validateName(name)
        validateSurname(surname)
        validateEmail(email)
        validateBirthday(birthday)
        validateGender(gender)
        validateCountry(country)
        validateCity(city)
    }

    // Name validation: only alphabetic characters and not empty
    private fun validateName(name: String) {
        require(name.isNotEmpty()) { "Name cannot be empty" }
        require(name.all { it.isLetter() || it.isWhitespace() }) { "Name must contain only letters and spaces" }
    }

    // Surname validation: only alphabetic characters and not empty
    private fun validateSurname(surname: String) {
        require(surname.isNotEmpty()) { "Surname cannot be empty" }
        require(surname.all { it.isLetter() || it.isWhitespace() }) { "Surname must contain only letters and spaces" }
    }

    // Email validation: basic email format check using regex
    private fun validateEmail(email: String) {
        val emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
        val pattern = Pattern.compile(emailPattern)
        require(pattern.matcher(email).matches()) { "Invalid email format" }
    }

    // Birthday validation: check if the date is in the correct format (e.g., "YYYY-MM-DD")
    private fun validateBirthday(birthday: String) {
        val datePattern = "^\\d{4}-\\d{2}-\\d{2}$"
        val pattern = Pattern.compile(datePattern)
        require(pattern.matcher(birthday).matches()) { "Birthday must be in the format YYYY-MM-DD" }
    }

    // Gender validation: ensure gender is either "Male", "Female", or "Other"
    private fun validateGender(gender: String) {
        val validGenders = setOf("Male", "Female", "Other")
        require(gender in validGenders) { "Gender must be either 'Male', 'Female', or 'Other'" }
    }

    // Country validation: ensure it's not empty and contains only letters and spaces
    private fun validateCountry(country: String) {
        require(country.isNotEmpty()) { "Country cannot be empty" }
        require(country.all { it.isLetter() || it.isWhitespace() }) { "Country must contain only letters and spaces" }
    }

    // City validation: ensure it's not empty and contains only letters and spaces
    private fun validateCity(city: String) {
        require(city.isNotEmpty()) { "City cannot be empty" }
        require(city.all { it.isLetter() || it.isWhitespace() }) { "City must contain only letters and spaces" }
    }
}
