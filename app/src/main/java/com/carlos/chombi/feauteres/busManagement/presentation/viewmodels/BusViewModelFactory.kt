package com.carlos.chombi.feauteres.busManagement.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.carlos.chombi.feauteres.busManagement.domain.usecases.*

class BusViewModelFactory(
    private val getAllBusesUseCase: GetAllBusesUseCase,
    private val addBusUseCase: AddBusUseCase,
    private val updateBusUseCase: UpdateBusUseCase,
    private val deleteBusUseCase: DeleteBusUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BusViewModel::class.java)) {
            return BusViewModel(
                getAllBusesUseCase,
                addBusUseCase,
                updateBusUseCase,
                deleteBusUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
