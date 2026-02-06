package com.carlos.chombi.feauteres.authentication.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.carlos.chombi.core.navegation.Bus
import com.carlos.chombi.core.navegation.FeatureNavGraph
import com.carlos.chombi.core.navegation.Home
import com.carlos.chombi.core.navegation.Login
import com.carlos.chombi.core.navegation.Register
import com.carlos.chombi.feauteres.authentication.di.AuthModule
import com.carlos.chombi.feauteres.authentication.presentation.screens.LoginScreen
import com.carlos.chombi.feauteres.authentication.presentation.screens.RegisterScreen
import com.carlos.chombi.feauteres.authentication.presentation.viewmodels.LoginViewModel
import com.carlos.chombi.feauteres.authentication.presentation.viewmodels.RegisterViewModel

class AuthNavGraph(
    private val authModule: AuthModule
) : FeatureNavGraph {

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {

        navGraphBuilder.composable<Login> {

            val viewModel: LoginViewModel = viewModel(
                factory = authModule.provideLoginViewModelFactory()
            )

            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LoginScreen(
                factory = authModule.provideLoginViewModelFactory(),
                onRegisterClick = {
                    navController.navigate(Register)
                },
                onBusClick = {
                    navController.navigate(Bus)
                },

            )


            //
            LaunchedEffect(uiState.isLoggedIn) {
                if (uiState.isLoggedIn) {
                    navController.navigate(Bus) {
                        popUpTo(Login) { inclusive = true }
                    }
                }
            }
        }


        navGraphBuilder.composable<Register> {

            val viewModel: RegisterViewModel = viewModel(
                factory = authModule.provideRegisterViewModelFactory()
            )

            RegisterScreen(
                factory = authModule.provideRegisterViewModelFactory(),
                onLoginClick = {
                    navController.navigate(Login)
                }
            )
        }
    }
}
