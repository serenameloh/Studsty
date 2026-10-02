package com.studsty.app.data.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "true_false_questions",
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
data class TrueFalseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val chapterId: Int,

    val statement: String,

    val correctAnswer: Boolean
)