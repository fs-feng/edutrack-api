package ch.edutrack.api.controller

import ch.edutrack.api.dto.CreateSemesterRequest
import ch.edutrack.api.dto.SemesterResponse
import ch.edutrack.api.service.SemesterService
import jakarta.annotation.security.RolesAllowed
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/semesters")
class SemesterController(private val semesterService: SemesterService) {

    @GetMapping
    @RolesAllowed("ADMIN")
    fun getAll(): ResponseEntity<List<SemesterResponse>> =
        ResponseEntity.ok(semesterService.getAll())

    @PostMapping
    @RolesAllowed("ADMIN")
    fun create(@RequestBody request: CreateSemesterRequest): ResponseEntity<SemesterResponse> =
        ResponseEntity.ok(semesterService.create(request))

    @PostMapping("/{id}/activate")
    @RolesAllowed("ADMIN")
    fun setActive(@PathVariable id: Long): ResponseEntity<SemesterResponse> =
        ResponseEntity.ok(semesterService.setActive(id))
}