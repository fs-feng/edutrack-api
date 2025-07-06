package ch.edutrack.api.repository

import ch.edutrack.api.model.SubjectEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface SubjectRepository : JpaRepository<SubjectEntity, Long> {
    fun findAllByIsActiveIsTrue(): List<SubjectEntity>
    fun findAllBySemesterId(semesterId: Long): List<SubjectEntity>
    fun findAllBySemesterIdAndIsActiveIsTrue(semesterId: Long): List<SubjectEntity>
    fun findByCodeIgnoreCase(code: String): SubjectEntity?
}