package com.studsty.app.data.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "progression",
    primaryKeys = ["chapterId", "contentType"],
    foreignKeys = [
        ForeignKey(
            entity = ChapterEntity::class,
            parentColumns = ["id"],
            childColumns = ["chapterId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("chapterId")]
)
data class ProgressionEntity(
    val chapterId: Int,

    val contentType: String,

    val completed: Boolean = false,

    val score: Int = 0,

    val totalQuestions: Int = 0,

    val lastActivity: Long = 0L
)