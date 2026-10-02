package com.studsty.app.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FlashcardDao {

    @Insert
    suspend fun insertFlashcard(flashcard: FlashcardEntity)

    @Insert
    suspend fun insertFlashcards(
        flashcards: List<FlashcardEntity>
    )

    @Query("""
        SELECT * FROM flashcards
        WHERE chapterId = :chapterId
    """)
    suspend fun getFlashcardsByChapter(
        chapterId: Int
    ): List<FlashcardEntity>
}