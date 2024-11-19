data class UserRegistration(
    val firstName: String,
    val lastName: String,
    val birthDate: String,
    val email: String,
    val password: String
)

data class UserResponse(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val birthDate: String
)

data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val token: String)

data class VerificationRequest(val token: String)
data class PasswordRecoveryRequest(val email: String)
data class RecoveryResponse(val success: Boolean, val message: String)

data class PasswordResetRequest(val token: String, val password: String)
data class ResetResponse(val success: Boolean)
