package com.studsty.app.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SubjectDao {

    @Insert
    suspend fun insertSubject(subject: SubjectEntity)

    @Insert
    suspend fun insertSubjects(
        subjects: List<SubjectEntity>
    )

    @Query("SELECT * FROM subjects")
    suspend fun getAllSubjects(): List<SubjectEntity>
}