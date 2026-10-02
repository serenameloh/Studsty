package data.model

data class Exercise (
    val id: Int,
    val chapterId: Int,
    val subject: String,
    val statement: String,
    val expectedAnswer: String,
    val keywords: List<String>
)
