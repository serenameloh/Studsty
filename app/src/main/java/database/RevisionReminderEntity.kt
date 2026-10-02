package com.studsty.app.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "revision_reminders")
data class RevisionReminderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val subject: String,

    val date: Long,

    val enabled: Boolean
)