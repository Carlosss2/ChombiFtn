package com.carlos.chombi.feauteres.authentication.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlos.chombi.feauteres.authentication.domain.usecases.RegisterUserUseCase
import com.carlos.chombi.feauteres.authentication.presentation.screens.RegisterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
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

        val currentState = _uiState.value

        val roleId = when (currentState.role) {
            "Checador" -> "46defe78-1d69-11f1-b7f4-16ffec603d6d"
            "Conductor" -> "UUID_DEL_CONDUCTOR"
            else -> ""
        }

        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {

            val result = registerUserUseCase(
                name = currentState.name,
                lastName = currentState.lastName,
                email = currentState.email,
                password = currentState.password,
                roleId = roleId
            )

            _uiState.update { state ->
                result.fold(
                    onSuccess = {
                        state.copy(isLoading = false, isSuccess = true)
                    },
                    onFailure = { exception ->
                        state.copy(isLoading = false, error = exception.message)
                    }
                )
            }
        }
    }
    fun onRoleChange(role: String) {
        _uiState.update { it.copy(role = role) }
    }
    fun clearResult() {
        _uiState.update {
            it.copy(
                isSuccess = false,
                error = null
            )
        }
    }
}