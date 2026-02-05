package com.carlos.chombi.feauteres.authentication.domain.entities

data class User(
    val name: String,
    val lastName: String,
    val email: String,
    val password: String

) {
}