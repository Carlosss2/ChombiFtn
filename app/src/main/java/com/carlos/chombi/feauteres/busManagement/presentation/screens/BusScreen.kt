package com.carlos.chombi.feauteres.busManagement.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.carlos.chombi.R
import com.carlos.chombi.core.shared.components.Header
import com.carlos.chombi.core.shared.components.Navbar
import com.carlos.chombi.core.ui.theme.primaryLight
import com.carlos.chombi.feauteres.busManagement.presentation.components.*
import com.carlos.chombi.feauteres.busManagement.presentation.viewmodels.BusViewModel

@Composable
fun BusScreen(
    viewModel: BusViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = primaryLight,
        bottomBar = { Navbar() }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            Header()

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Unidades registradas",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Button(
                    onClick = viewModel::openAddDialog,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.add),
                        contentDescription = "Agregar",
                        tint = primaryLight,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            uiState.buses.forEach { bus ->
                CardBus(
                    unidad = bus.unitNumber.toString(),
                    chofer = bus.driver,
                    placa = bus.licencePlate,
                    onEditClick = {
                        viewModel.selectBus(bus)
                        viewModel.openEditDialog()
                    },
                    onDeleteClick = {
                        viewModel.selectBus(bus)
                        viewModel.openDeleteDialog()
                    }
                )
            }
        }
    }

    /* ---------------- DIALOGS ---------------- */

    if (uiState.showAddDialog) {
        AddBusDialog(
            onDismiss = viewModel::closeDialogs,
            onSave = viewModel::addBus
        )
    }

    if (uiState.showEditDialog && uiState.selectedBus != null) {
        EditBusDialog(
            bus = uiState.selectedBus!!,
            onDismiss = viewModel::closeDialogs,
            onSave = viewModel::updateSelectedBus
        )
    }

    if (uiState.showDeleteDialog && uiState.selectedBus != null) {
        DeleteBusDialog(
            onConfirm = viewModel::deleteSelectedBus,
            onDismiss = viewModel::closeDialogs
        )
    }
}
