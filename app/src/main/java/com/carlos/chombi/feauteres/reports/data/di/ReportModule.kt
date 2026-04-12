package com.carlos.chombi.feauteres.reports.data.di


import com.carlos.chombi.feauteres.reports.data.repositories.ReportRepositoryImpl
import com.carlos.chombi.feauteres.reports.domain.repositories.ReportRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class ReportModule {
    @Binds
    abstract fun bindChombiRepository(
        chombiRepositoryImpl: ReportRepositoryImpl
    ): ReportRepository
}