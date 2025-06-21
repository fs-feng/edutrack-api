package ch.edutrack.api.service

import ch.edutrack.api.dto.CreateSemesterRequest
import ch.edutrack.api.dto.SemesterResponse
import ch.edutrack.api.model.SemesterEntity
import ch.edutrack.api.repository.SemesterRepository
import org.springframework.stereotype.Service

@Service
class SemesterService (
    private val semesterRepository: SemesterRepository
){
    fun getAll(): List<SemesterResponse> =
        semesterRepository.findAllByOrderByStartDateDesc().map { it.toDTO() }


    fun create(request: CreateSemesterRequest): SemesterResponse {
        val semester = SemesterEntity(
            name = request.name,
            startDate = request.startDate,
            endDate = request.endDate
        )
        return semesterRepository.save(semester).toDTO()
    }

    fun setActive(id: Long): SemesterResponse {
        semesterRepository.findAll().forEach { it.isActive = false }

        val semester = semesterRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Semester not found") }
        semester.isActive = true

        return semesterRepository.save(semester).toDTO()
    }

    private fun SemesterEntity.toDTO() = SemesterResponse(
        id = id!!,
        name = name,
        startDate = startDate,
        endDate = endDate,
        isActive = isActive
    )
}