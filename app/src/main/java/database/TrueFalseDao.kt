package com.studsty.app.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TrueFalseDao {

    @Insert
    suspend fun insertQuestion(question: TrueFalseEntity)

    @Insert
    suspend fun insertQuestions(
        questions: List<TrueFalseEntity>
    )

    @Query("""
        SELECT * FROM true_false_questions
        WHERE chapterId = :chapterId
    """)
    suspend fun getQuestionsByChapter(
        chapterId: Int
    ): List<TrueFalseEntity>
}