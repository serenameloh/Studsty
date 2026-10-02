package data.model

data class TrueFalseQuestion (
    val id: Int,
    val chapterId: Int,
    val question: String,
    val correctAnswer: Boolean
)