package ch.edutrack.api.dto

import java.time.LocalDate

data class SemesterResponse(
    val id: Long,
    val name: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val isActive: Boolean
)