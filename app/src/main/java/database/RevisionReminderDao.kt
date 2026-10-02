package com.studsty.app.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RevisionReminderDao {

    @Insert
    suspend fun insertReminder(
        reminder: RevisionReminderEntity
    )

    @Query("""
        SELECT * FROM revision_reminders
        ORDER BY date ASC
    """)
    suspend fun getAllReminders(): List<RevisionReminderEntity>
}