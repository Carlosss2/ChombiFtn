package com.carlos.chombi.feauteres.authentication.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.carlos.chombi.feauteres.authentication.domain.usecases.AuthUserUseCase

class LoginViewModelFactory(
    private val loginUserUseCase: AuthUserUseCase // Cambiado para ser consistente
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(loginUserUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}