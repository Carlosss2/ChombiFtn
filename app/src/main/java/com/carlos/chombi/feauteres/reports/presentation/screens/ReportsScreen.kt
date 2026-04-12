package com.carlos.chombi.feauteres.reports.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.carlos.chombi.core.shared.components.Navbar
import com.carlos.chombi.core.ui.theme.onPrimaryLight
import com.carlos.chombi.feauteres.reports.presentation.components.HeaderReport
import com.carlos.chombi.feauteres.reports.presentation.viewmodels.ReportViewModel

@Composable
fun ReportsScreen(viewModel: ReportViewModel = hiltViewModel()){
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = onPrimaryLight,

        bottomBar = {
            Navbar(
                onHomeClick = { viewModel.goHome() },
                onAddClick = { viewModel.goToAddBus() },
                onHistoryClick = { viewModel.goToHistory() },
                onReportsClick = {viewModel.goToReports()}
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            HeaderReport()
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "Reportes",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
        }
@Preview(showBackground = true)
@Composable
fun PreviewReportScreen(){
    ReportsScreen()
}