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
                onHistoryClick = { viewModel.goToHistory() }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Encabezado fijo en la parte superior
            HeaderBus(onAddClick = { viewModel.openAddDialog() })

            Spacer(modifier = Modifier.height(40.dp))

            // Título fijo
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Unidades registradas",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // LAZYCOLUMN: Solo esta parte hará scroll
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), // Toma el espacio restante en la pantalla
                contentPadding = PaddingValues(bottom = 16.dp) // Espaciado al final de la lista
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