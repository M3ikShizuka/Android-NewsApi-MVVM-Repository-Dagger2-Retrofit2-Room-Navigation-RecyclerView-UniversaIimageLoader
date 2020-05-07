package com.m3ikshizuka.worldnews

import android.app.Application
import com.m3ikshizuka.worldnews.common.LoadImage
import com.m3ikshizuka.worldnews.di.AppComponent
import com.m3ikshizuka.worldnews.di.AppModule
import com.m3ikshizuka.worldnews.di.DaggerAppComponent

class App : Application() {
    val component: AppComponent by lazy {
//        DaggerAppComponent.create()
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        this.component.inject(this)
        // Init load image singleton.
        LoadImage(this)
    }
}