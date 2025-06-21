package ch.edutrack.api.repository


import ch.edutrack.api.model.SemesterEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SemesterRepository : JpaRepository<SemesterEntity, Long> {
    fun findByIsActiveTrue(): SemesterEntity?
    fun findAllByOrderByStartDateDesc(): List<SemesterEntity>
}