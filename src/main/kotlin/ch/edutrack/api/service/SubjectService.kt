package ch.edutrack.api.service

import ch.edutrack.api.dto.CreateSubjectRequestDTO
import ch.edutrack.api.dto.SemesterSummaryDTO
import ch.edutrack.api.dto.SubjectResponseDTO
import ch.edutrack.api.dto.UpdateSubjectRequestDTO
import ch.edutrack.api.model.SubjectEntity
import ch.edutrack.api.repository.SemesterRepository
import ch.edutrack.api.repository.SubjectRepository
import org.springframework.stereotype.Service

@Service
class SubjectService (
    private val subjectRepository: SubjectRepository,
    private val semesterRepository: SemesterRepository
) {
    fun getAll(): List<SubjectResponseDTO> =
        subjectRepository.findAllByIsActiveIsTrue().map { it.toDTO() }

    fun getAllBySemester(semesterId: Long): List<SubjectResponseDTO> =
        subjectRepository.findAllBySemesterIdAndIsActiveIsTrue(semesterId).map { it.toDTO() }

    fun getById(id: Long): SubjectResponseDTO {
        val subject = subjectRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Subject not found") }
        return subject.toDTO()
    }


    fun create(request: CreateSubjectRequestDTO): SubjectResponseDTO {
        val semester = semesterRepository.findById(request.semesterId)
            .orElseThrow { IllegalArgumentException("Semester not found") }

        val subject = SubjectEntity(
            name = request.name,
            description = request.description,
            code = request.code.uppercase(),
            semester = semester
        )

        return subjectRepository.save(subject).toDTO()
    }


    fun update(id: Long, request: UpdateSubjectRequestDTO): SubjectResponseDTO {
        val subject = subjectRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Subject not found") }

        val semester = semesterRepository.findById(request.semsterId)
            .orElseThrow { IllegalArgumentException("Semester not found") }

        subjectRepository.findByCodeIgnoreCase(request.code)?.let { existing ->
            if (existing.id != id) {
                throw IllegalArgumentException("Subject code already exists")
            }
        }

        subject.name = request.name
        subject.description = request.description
        subject.code = request.code.uppercase()
        subject.isActive = request.isActive
        subject.semester = semester

        return subjectRepository.save(subject).toDTO()
    }


    fun toggleStatus(id: Long): SubjectResponseDTO {
        val subject = subjectRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Subject not found") }

        subject.isActive = !subject.isActive
        return subjectRepository.save(subject).toDTO()
    }

    fun delete(id: Long) {
        val subject = subjectRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Subject not found") }

        subject.isActive = false
        subjectRepository.save(subject)
    }



    private fun SubjectEntity.toDTO() = SubjectResponseDTO(
        id = id!!,
        name = name,
        description = description,
        code = code,
        isActive = isActive,
        semester = SemesterSummaryDTO(
            id = semester.id!!,
            name = semester.name,
            isActive = isActive
        )

    )
}