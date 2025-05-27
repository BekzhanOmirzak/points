package com.task.points.di

import com.task.points.data.repository.PointsRepositoryImpl
import com.task.points.data.repository.SaveChartRepositoryImpl
import com.task.points.domain.repository.PointsRepository
import com.task.points.domain.repository.SaveChartRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun bindPointsRepository(
        impl: PointsRepositoryImpl
    ): PointsRepository

    @Binds
    fun bindSaveChartRepository(
        impl: SaveChartRepositoryImpl
    ): SaveChartRepository

}