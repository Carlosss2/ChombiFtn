package com.carlos.chombi.feauteres.authentication.domain.usecases

import com.carlos.chombi.feauteres.authentication.domain.entities.User
import com.carlos.chombi.feauteres.authentication.domain.repositories.AuthRepository

class AuthUserUseCase(
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

            Result.failure(e)
        }
    }
}