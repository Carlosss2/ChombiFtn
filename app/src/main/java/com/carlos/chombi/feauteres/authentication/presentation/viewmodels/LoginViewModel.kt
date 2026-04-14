package com.carlos.chombi.feauteres.authentication.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlos.chombi.core.hardware.domain.BiometricManager
import com.carlos.chombi.core.navigation.AppNavigator
import com.carlos.chombi.core.session.TokenDataStore // <-- IMPORTANTE
import com.carlos.chombi.feauteres.authentication.domain.usecases.AuthUserUseCase
import com.carlos.chombi.feauteres.authentication.navigation.AuthRoutes
import com.carlos.chombi.feauteres.authentication.presentation.screens.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull // <-- IMPORTANTE
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: AuthUserUseCase,
    private val navigator: AppNavigator,
    private val biometricManager: BiometricManager,
    private val tokenDataStore: TokenDataStore //
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun clearResult() {
        _uiState.update {
            it.copy(
                error = null,
                isLoggedIn = false
            )
        }
    }

    fun login() {
        val currentState = _uiState.value

        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            val result = loginUseCase(
                currentState.email,
                currentState.password
            )

            _uiState.update { state ->
                result.fold(
                    onSuccess = {
                        navigator.navigate("home_graph") { popUpTo(AuthRoutes.AUTH_GRAPH) { inclusive = true } }
                        state.copy(isLoading = false)
                    },
                    onFailure = { exception ->
                        state.copy(isLoading = false, error = exception.message)
                    }
                )
            }
        }
    }

    fun loginWithBiometrics(context: Context) {
        viewModelScope.launch {
            // Obtenemos el token
            val token = tokenDataStore.getToken()

            // Usamos una validación 100% segura para nulos
            if (token == null) {
                _uiState.update {
                    it.copy(error = "Primero debes iniciar sesión con correo y contraseña para vincular tu huella.")
                }
                return@launch
            }

            // Si ya tiene token, lanzamos el hardware biométrico
            val result = biometricManager.authenticateBiometric(context, "login_request")

            result.fold(
                onSuccess = {
                    navigator.navigate("home_graph") {
                        popUpTo(AuthRoutes.AUTH_GRAPH) { inclusive = true }
                    }
                },
                onFailure = { exception ->
                    _uiState.update {
                        it.copy(error = exception.message)
                    }
                }
            )
        }
    }

    fun goToRegister() {
        navigator.navigate(AuthRoutes.REGISTER) {
            popUpTo(AuthRoutes.LOGIN) { inclusive = true }
        }
    }
}