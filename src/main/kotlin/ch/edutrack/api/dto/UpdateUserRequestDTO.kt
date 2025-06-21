package ch.edutrack.api.dto

data class UpdateUserRequestDTO(
    val username: String,
    val email: String,
    val password: String?,
    val firstName: String?,
    val lastName: String?,
    val isActive: Boolean,
    val roleIds: List<Long>
)