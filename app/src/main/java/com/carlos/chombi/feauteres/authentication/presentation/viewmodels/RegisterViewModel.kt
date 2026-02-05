package com.carlos.chombi.feauteres.authentication.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlos.chombi.feauteres.authentication.domain.usecases.RegisterUserUseCase
import com.carlos.chombi.feauteres.authentication.presentation.screens.RegisterUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    //
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()


    fun onNameChange(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun onLastNameChange(lastName: String) {
        _uiState.update { it.copy(lastName = lastName) }
    }

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    //  Función principal de Registro

    fun onRegister() {
        // Obtenemos los valores actuales del estado
        val currentState = _uiState.value

        // Activamos el loading y limpiamos errores previos
        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            // Llamamos al UseCase
            val result = registerUserUseCase(
                name = currentState.name,
                lastName = currentState.lastName,
                email = currentState.email,
                password = currentState.password
            )

            // Manejamos el resultado usando fold (como en tu ejemplo)
            _uiState.update { state ->
                result.fold(
                    onSuccess = {
                        // Registro exitoso: quitamos loading y marcamos éxito
                        state.copy(isLoading = false, isSuccess = true)
                    },
                    onFailure = { exception ->
                        // Error: quitamos loading y mostramos el mensaje
                        state.copy(isLoading = false, error = exception.message)
                    }
                )
            }
        }
    }
}