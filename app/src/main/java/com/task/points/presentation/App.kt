package com.task.points.presentation

import android.app.Application
import com.task.points.di.AppComponent
import com.task.points.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }

}