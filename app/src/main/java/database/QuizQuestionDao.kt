package com.studsty.app.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface QuizQuestionDao {

    @Insert
    suspend fun insertQuestion(question: QuizQuestionEntity)

    @Insert
    suspend fun insertQuestions(questions: List<QuizQuestionEntity>)

    @Query("""
        SELECT * FROM quiz_questions
        WHERE chapterId = :chapterId
    """)
    suspend fun getQuestionsByChapter(
        chapterId: Int
    ): List<QuizQuestionEntity>
}