package com.carlos.chombi.feauteres.authentication.presentation.screens

data class RegisterUiState(
    val name: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val role: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false // Para saber cuándo navegar al Login/Home
)