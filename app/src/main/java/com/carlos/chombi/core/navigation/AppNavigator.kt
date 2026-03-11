package com.carlos.chombi.core.navigation

import androidx.navigation.NavOptionsBuilder

interface AppNavigator {
    fun navigate(
        route: String,
        builder: (NavOptionsBuilder.() -> Unit)? = null
    )
    fun popBackStack()
}