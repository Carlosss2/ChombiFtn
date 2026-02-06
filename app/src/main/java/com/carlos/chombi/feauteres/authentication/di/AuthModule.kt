package com.carlos.chombi.feauteres.authentication.di

import com.carlos.chombi.core.di.AppContainer
import com.carlos.chombi.feauteres.authentication.domain.usecases.AuthUserUseCase
import com.carlos.chombi.feauteres.authentication.domain.usecases.RegisterUserUseCase
import com.carlos.chombi.feauteres.authentication.presentation.viewmodels.LoginViewModelFactory
import com.carlos.chombi.feauteres.authentication.presentation.viewmodels.RegisterViewModelFactory

class AuthModule(
    private val appContainer: AppContainer
) {

    private fun provideRegisterUserUseCase(): RegisterUserUseCase {
        return RegisterUserUseCase(appContainer.authRepository)
    }

    private fun provideLoginUserUseCase(): AuthUserUseCase {
        return AuthUserUseCase(appContainer.authRepository)
    }

    fun provideRegisterViewModelFactory(): RegisterViewModelFactory {
        return RegisterViewModelFactory(
            registerUserUseCase = provideRegisterUserUseCase()
        )
    }

    fun provideLoginViewModelFactory(): LoginViewModelFactory {
        return LoginViewModelFactory(
            loginUserUseCase = provideLoginUserUseCase()
        )
    }
}
