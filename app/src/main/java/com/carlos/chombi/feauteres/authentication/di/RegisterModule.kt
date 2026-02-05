package com.carlos.chombi.feauteres.authentication.di

import com.carlos.chombi.core.di.AppContainer
import com.carlos.chombi.feauteres.authentication.domain.usecases.RegisterUserUseCase
import com.carlos.chombi.feauteres.authentication.presentation.viewmodels.RegisterViewModelFactory

class RegisterModule(
    private val appContainer : AppContainer
){
    private fun getProvideRegisterUserUseCase(): RegisterUserUseCase{
        return RegisterUserUseCase(appContainer.AuthRepository)
    }

    fun privateGetProvideRegisterUserViewModelFactory(): RegisterViewModelFactory{
        return RegisterViewModelFactory(registerUserUseCase = getProvideRegisterUserUseCase())
    }
}