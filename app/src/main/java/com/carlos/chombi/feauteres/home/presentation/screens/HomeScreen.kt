package com.carlos.chombi.feauteres.home.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.carlos.chombi.core.shared.components.Navbar
import com.carlos.chombi.core.ui.theme.onPrimaryLight
import com.carlos.chombi.features.home.presentation.screens.DispatchScreen
import com.carlos.chombi.feauteres.home.presentation.components.HeaderHome
import com.carlos.chombi.feauteres.home.presentation.viewmodels.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = onPrimaryLight,

        bottomBar = {
            Navbar(
                onHomeClick = { viewModel.goHome() },
                onAddClick = { viewModel.goToAddBus() },
                onHistoryClick = { viewModel.goToHistory() }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            HeaderHome()


            Spacer(modifier = Modifier.height(20.dp))
            DispatchScreen()


        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen(){
    HomeScreen()
}