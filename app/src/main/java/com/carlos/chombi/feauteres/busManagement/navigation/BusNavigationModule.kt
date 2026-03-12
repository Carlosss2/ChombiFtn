package com.carlos.chombi.feauteres.busManagement.navigation

import com.carlos.chombi.core.navigation.FeatureNavGraph

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class BusNavigationModule {

    @Binds
    @IntoSet
    abstract fun bindBusNavGraph(graph: BusNavGraph): FeatureNavGraph
}