package com.task.points.di

import android.app.Application
import android.content.Context
import com.task.points.presentation.points.PointsActivity
import com.task.points.presentation.points.PointsPresenter
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, RepositoryModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Application): AppComponent
    }

    fun inject(activity: PointsActivity)
    fun provideMainPresenter(): PointsPresenter
}