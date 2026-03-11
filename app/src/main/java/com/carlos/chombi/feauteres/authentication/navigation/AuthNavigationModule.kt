package com.carlos.chombi.feauteres.authentication.navigation

import com.carlos.chombi.core.navigation.FeatureNavGraph
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthNavigationModule {

    @Binds
    @IntoSet
    abstract fun bindAuthNavGraph(
        impl: AuthNavGraph
    ): FeatureNavGraph
}