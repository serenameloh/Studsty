package com.studsty.app.data.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "exercises",
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
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val chapterId: Int,

    val statement: String,

    val expectedAnswer: String,
    val keywords: String
)