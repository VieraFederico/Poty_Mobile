package hci.mobile.poty.classes;

data class LoginForm(
        val email: String,
        val password: String
) {
    // Function to check if the form is valid (both fields are not empty)
    fun isValid(): Boolean {
        return email.isNotBlank() && password.isNotBlank()
    }
}
