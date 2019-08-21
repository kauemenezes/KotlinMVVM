package com.codingwithmitch.foodrecipes

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

class AppExecutors {

    val networkIO: ScheduledExecutorService  = Executors.newScheduledThreadPool(3)

    companion object {

        private var instance: AppExecutors? = null

        fun getInstance(): AppExecutors {
            if (instance == null) {
                instance = AppExecutors()
            }
            return instance!!
        }
    }
}