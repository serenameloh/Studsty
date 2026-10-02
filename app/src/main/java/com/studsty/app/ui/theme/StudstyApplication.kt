package com.studsty.app

import android.app.Application
import com.studsty.app.data.database.DatabaseInitializer
import com.studsty.app.data.database.DatabaseProvider
import data.repository.StudstyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class StudstyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val database = DatabaseProvider.getDatabase(this)
        val repository = StudstyRepository(this)

        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            DatabaseInitializer.initialize(
                database,
                repository
            )
        }
    }
}