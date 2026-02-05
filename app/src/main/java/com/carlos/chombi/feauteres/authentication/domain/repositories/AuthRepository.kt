package com.carlos.chombi.feauteres.authentication.domain.repositories

import com.carlos.chombi.feauteres.authentication.domain.entities.User

interface AuthRepository {
    suspend fun authUser(email: String, password: String): User
    suspend fun registerUser(user: User): User
}