package data.model

data class RevisionReminder(
    val id: Int,
    val subject: String,
    val date: Long,
    val enabled: Boolean
)