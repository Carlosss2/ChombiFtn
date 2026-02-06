package com.carlos.chombi.feauteres.busManagement.di

import com.carlos.chombi.core.di.AppContainer
import com.carlos.chombi.feauteres.busManagement.domain.usecases.AddBusUseCase
import com.carlos.chombi.feauteres.busManagement.domain.usecases.DeleteBusUseCase
import com.carlos.chombi.feauteres.busManagement.domain.usecases.GetAllBusesUseCase
import com.carlos.chombi.feauteres.busManagement.domain.usecases.UpdateBusUseCase
import com.carlos.chombi.feauteres.busManagement.presentation.viewmodels.BusViewModelFactory

class BusModule(
    private val appContainer: AppContainer
) {


    private fun provideGetAllBusesUseCase(): GetAllBusesUseCase {
        return GetAllBusesUseCase(
            repository = appContainer.busRepository
        )
    }

    private fun provideAddBusUseCase(): AddBusUseCase {
        return AddBusUseCase(
            repository = appContainer.busRepository
        )
    }

    private fun provideUpdateBusUseCase(): UpdateBusUseCase {
        return UpdateBusUseCase(
            repository = appContainer.busRepository
        )
    }

    private fun provideDeleteBusUseCase(): DeleteBusUseCase {
        return DeleteBusUseCase(
            repository = appContainer.busRepository
        )
    }



    fun provideBusViewModelFactory(): BusViewModelFactory {
        return BusViewModelFactory(
            getAllBusesUseCase = provideGetAllBusesUseCase(),
            addBusUseCase = provideAddBusUseCase(),
            updateBusUseCase = provideUpdateBusUseCase(),
            deleteBusUseCase = provideDeleteBusUseCase()
        )
    }
}
