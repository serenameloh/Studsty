package data.model

data class Question(
    val id: Int,
    val chapterId: Int,
    val subject: String,
    val question: String,
    val options: List<String>,
    val correctAnswer: Int
)