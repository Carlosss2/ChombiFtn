package com.carlos.chombi.feauteres.authentication.domain.usecases

import android.util.Log
import com.carlos.chombi.feauteres.authentication.domain.entities.User
import com.carlos.chombi.feauteres.authentication.domain.repositories.AuthRepository
import javax.inject.Inject

class AuthUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        return try {
            if (email.isBlank() || password.isBlank()) {
                return Result.failure(Exception("El correo y la contraseña son obligatorios."))
            }

            val user = repository.authUser(email, password)

            Result.success(user)

        } catch (e: Exception) {
            Log.e("AuthError", "Error real al iniciar sesión: ${e.message}", e)
            Result.failure(e)
        }
    }
}