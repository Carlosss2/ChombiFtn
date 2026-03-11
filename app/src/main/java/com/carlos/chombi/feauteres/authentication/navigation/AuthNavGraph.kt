package com.carlos.chombi.feauteres.authentication.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.carlos.chombi.core.navigation.FeatureNavGraph

import com.carlos.chombi.feauteres.authentication.presentation.screens.LoginScreen
import com.carlos.chombi.feauteres.authentication.presentation.screens.RegisterScreen
import javax.inject.Inject

class AuthNavGraph @Inject constructor() : FeatureNavGraph {

    override fun register(builder: NavGraphBuilder) {
        builder.navigation(
            route = AuthRoutes.AUTH_GRAPH,
            startDestination = AuthRoutes.LOGIN
        ) {
            composable(AuthRoutes.LOGIN) {
                LoginScreen(
                    onRegisterClick = {
                        // TODO: Navigate to Register Route
                    },
                    onBusClick = {
                        // TODO: Navigate to Bus/Home Route
                    }
                )
            }

            composable(AuthRoutes.REGISTER) {
                RegisterScreen(onLoginClick = {
                    // TODO: Navigate to Login Route
                })
            }
        }
    }
}