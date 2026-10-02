package com.studsty.app.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StudySessionDao {

    @Insert
    suspend fun insertSession(
        session: StudySessionEntity
    )

    @Query("""
        SELECT * FROM study_sessions
        ORDER BY date DESC
    """)
    suspend fun getAllSessions(): List<StudySessionEntity>

    @Query("""
        SELECT * FROM study_sessions
        WHERE chapterId = :chapterId
        ORDER BY date DESC
    """)
    suspend fun getSessionsByChapter(
        chapterId: Int
    ): List<StudySessionEntity>
}