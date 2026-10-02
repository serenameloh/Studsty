package com.studsty.app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        SubjectEntity::class,
        ChapterEntity::class,
        QuizQuestionEntity::class,
        FlashcardEntity::class,
        TrueFalseEntity::class,
        ExerciseEntity::class,
        StudySessionEntity::class,
        ProgressionEntity::class,
        RevisionReminderEntity::class
    ],
    version = 9,
    exportSchema = false
)
abstract class StudstyDatabase : RoomDatabase() {

    abstract fun subjectDao(): SubjectDao
    abstract fun chapterDao(): ChapterDao
    abstract fun quizQuestionDao(): QuizQuestionDao
    abstract fun flashcardDao(): FlashcardDao
    abstract fun trueFalseDao(): TrueFalseDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun studySessionDao(): StudySessionDao
    abstract fun progressionDao(): ProgressionDao
    abstract fun revisionReminderDao(): RevisionReminderDao
}