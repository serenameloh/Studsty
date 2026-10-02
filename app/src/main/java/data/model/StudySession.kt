package data.model
class StudySession(
    val id: Int,
    val chapterId: Int,
    val subject: String,
    val type: String,
    val score: Int,
    val total: Int,
    val date: Long
)