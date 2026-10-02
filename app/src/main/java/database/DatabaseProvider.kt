package com.studsty.app.data.database

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    @Volatile
    private var INSTANCE: StudstyDatabase? = null

    fun getDatabase(context: Context): StudstyDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                StudstyDatabase::class.java,
                "studsty_database"
            )
                .build()
                .also { INSTANCE = it}
            INSTANCE = instance
            instance
        }
    }
}