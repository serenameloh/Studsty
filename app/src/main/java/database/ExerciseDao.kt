package com.studsty.app.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExerciseDao {

    @Insert
    suspend fun insertExercise(
        exercise: ExerciseEntity
    )

    @Insert
    suspend fun insertExercises(
        exercises: List<ExerciseEntity>
    )

    @Query("""
        SELECT * FROM exercises
        WHERE chapterId = :chapterId
    """)
    suspend fun getExercisesByChapter(
        chapterId: Int
    ): List<ExerciseEntity>
}