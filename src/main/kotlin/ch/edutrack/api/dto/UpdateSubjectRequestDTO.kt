package ch.edutrack.api.dto

data class UpdateSubjectRequestDTO(
    val name: String,
    val description: String?,
    val code: String,
    val isActive: Boolean,
    val semsterId: Long
)
