package com.studsty.app.data.database

import data.repository.StudstyRepository

object DatabaseInitializer {

    suspend fun initialize(
        database: StudstyDatabase,
        repository: StudstyRepository
    ) {

        // Vérifie si la base contient déjà les matières
        if (database.subjectDao().getAllSubjects().isNotEmpty()) {
            return
        }

        // -------------------------
        // 1. Matières
        // -------------------------

        val subjects = repository.getSubjects().map { subject ->
            SubjectEntity(
                id = subject.id,
                name = subject.name
            )
        }

        database.subjectDao().insertSubjects(subjects)

        // -------------------------
        // 2. Chapitres
        // -------------------------

        val chapters = repository.getAllChapters().map { chapter ->

            val order = repository
                .getChaptersByCourse(chapter.courseId)
                .indexOfFirst { it.id == chapter.id } + 1

            ChapterEntity(
                id = chapter.id,
                subjectId = chapter.courseId,
                title = chapter.title,
                chapterOrder = order
            )
        }

        database.chapterDao().insertChapters(chapters)

        // -------------------------
        // 3. QCM
        // -------------------------

        val questions = repository.getAllQuestions().map { question ->
            QuizQuestionEntity(
                id = question.id,
                chapterId = question.chapterId,
                question = question.question,
                optionA = question.options[0],
                optionB = question.options[1],
                optionC = question.options[2],
                optionD = question.options[3],
                correctAnswer = question.correctAnswer.toString()
            )
        }

        database.quizQuestionDao().insertQuestions(questions)

        // -------------------------
        // 4. Flashcards
        // -------------------------

        val flashcards = repository.getAllFlashcards().map { flashcard ->
            FlashcardEntity(
                id = flashcard.id,
                chapterId = flashcard.chapterId,
                question = flashcard.question,
                answer = flashcard.answer
            )
        }

        database.flashcardDao().insertFlashcards(flashcards)

        // -------------------------
        // 5. Vrai / Faux
        // -------------------------

        val trueFalseQuestions =
            repository.getAllTrueFalseQuestions().map { question ->
                TrueFalseEntity(
                    id = question.id,
                    chapterId = question.chapterId,
                    statement = question.question,
                    correctAnswer = question.correctAnswer
                )
            }

        database.trueFalseDao().insertQuestions(trueFalseQuestions)

        // -------------------------
        // 6. Exercices
        // -------------------------

        val exercises = repository.getAllExercises().map { exercise ->
            ExerciseEntity(
                id = exercise.id,
                chapterId = exercise.chapterId,
                statement = exercise.statement,
                expectedAnswer = exercise.expectedAnswer,
                keywords = exercise.keywords.joinToString("|")
            )
        }

        database.exerciseDao().insertExercises(exercises)
    }
}