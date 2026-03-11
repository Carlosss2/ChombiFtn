package com.carlos.chombi.feauteres.authentication.domain.usecases

import com.carlos.chombi.feauteres.authentication.domain.entities.User
import com.carlos.chombi.feauteres.authentication.domain.repositories.AuthRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        name: String,
        lastName: String,
        email: String,
        password: String,
        roleId: String
    ): Result<User> {
        return try {
            // Verificar campos vacíos
            if (name.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()) {
                return Result.failure(Exception("Todos los campos son obligatorios"))
            }

            // Verificar formato de email (básico)
            if (!email.contains("@")) {
                return Result.failure(Exception("El formato del correo no es válido"))
            }


            if (password.length < 8) {
                return Result.failure(Exception("La contraseña debe tener al menos 8 caracteres"))
            }


            val newUser = User(
                name = name,
                lastName = lastName,
                email = email,
                password = password,
                roleId = roleId
            )
            val registeredUser = repository.registerUser(newUser)

            Result.success(registeredUser)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}