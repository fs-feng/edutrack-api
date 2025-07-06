package ch.edutrack.api.dto

data class CreateSubjectRequestDTO(
    val name: String,
    val description: String?,
    val code: String,
    val semesterId: Long
)
