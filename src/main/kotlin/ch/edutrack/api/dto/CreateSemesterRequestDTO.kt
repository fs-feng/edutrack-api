package ch.edutrack.api.dto

import java.time.LocalDate

data class CreateSemesterRequest(
    val name: String,
    val startDate: LocalDate,
    val endDate: LocalDate
)