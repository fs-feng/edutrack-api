package ch.edutrack.api.dto

data class SubjectResponseDTO(
    val id: Long,
    val name: String,
    val description: String?,
    val code: String,
    val isActive: Boolean,
    val semester: SemesterSummaryDTO
)
