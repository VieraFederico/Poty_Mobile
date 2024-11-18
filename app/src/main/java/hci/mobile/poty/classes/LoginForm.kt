package hci.mobile.poty.classes;

data class LoginForm(
        val email: String,
        val password: String
) {
    fun isValid(): Boolean {
        return email.isNotBlank() && password.isNotBlank()
    }
}
