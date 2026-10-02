package data.model

data class Flashcard (
    val id: Int,
    val chapterId: Int,
    val question: String,
    val answer: String
)
