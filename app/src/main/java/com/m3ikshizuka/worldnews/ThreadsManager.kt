package com.m3ikshizuka.worldnews

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Singleton

@Singleton
class ThreadsManager {
    private val executor: ExecutorService

    init {
        this.executor = Executors.newFixedThreadPool(3)
    }

    fun execute(runnable: Runnable) {
        this.executor.execute(runnable)
    }

    fun shutdown() {
        this.executor.shutdown()
    }
}