package com.carlos.chombi.feauteres.busManagement.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import com.carlos.chombi.core.shared.components.Navbar
import com.carlos.chombi.core.ui.theme.onPrimaryLight
import com.carlos.chombi.feauteres.busManagement.presentation.components.*
import com.carlos.chombi.feauteres.busManagement.presentation.viewmodels.BusViewModel

@Composable
fun BusScreen(
    viewModel: BusViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val currentPhoto by viewModel.currentPhoto.collectAsStateWithLifecycle()

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

            HeaderBus(
                onAddClick = { viewModel.openAddDialog() },
                onSearchClick = { unit -> viewModel.searchBusByUnit(unit) }
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Título fijo y botón para recargar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp), // Aplicamos padding al Row
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Unidades registradas",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                TextButton(onClick = { viewModel.loadBuses() }) {
                    Text("Ver todas", color = MaterialTheme.colorScheme.primary)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))


            if (uiState.error != null && uiState.buses.isEmpty()) {
                Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                    Text(text = "Unidad no encontrada", color = Color.Gray, fontSize = 16.sp)
                }
            } else {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(uiState.buses) { bus ->
                        CardBus(
                            unidad = bus.unitNumber.toString(),
                            chofer = bus.driver,
                            placa = bus.licencePlate,
                            modelo = bus.model,
                            imageUrl = bus.imageUrl,
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
        }
    }

    // Diálogos emergentes
    if (uiState.showAddDialog) {
        AddBusDialog(
            onDismiss = viewModel::closeDialogs,
            onSave = viewModel::addBus,
            onTakePhotoClick = viewModel::takePhoto,
            currentPhoto = currentPhoto
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