package com.studsty.app.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ChapterDao {

    @Insert
    suspend fun insertChapter(chapter: ChapterEntity)

    @Insert
    suspend fun insertChapters(chapters: List<ChapterEntity>)

    @Query("""
        SELECT * FROM chapters
        WHERE subjectId = :subjectId
        ORDER BY chapterOrder ASC
    """)
    suspend fun getChaptersBySubject(subjectId: Int): List<ChapterEntity>
}