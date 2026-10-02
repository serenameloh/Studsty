package com.studsty.app.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProgressionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProgression(
        progression: ProgressionEntity
    )

    @Query("""
        SELECT * FROM progression
        WHERE chapterId = :chapterId
    """)
    suspend fun getProgressionByChapter(
        chapterId: Int
    ): List<ProgressionEntity>

    @Query("""
        SELECT * FROM progression
    """)
    suspend fun getAllProgression(): List<ProgressionEntity>
}