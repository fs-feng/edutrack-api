package ch.edutrack.api.controller

import ch.edutrack.api.dto.CreateUserRequestDTO
import ch.edutrack.api.dto.UpdateUserRequestDTO
import ch.edutrack.api.dto.UserResponseDTO
import ch.edutrack.api.service.UserService
import jakarta.annotation.security.RolesAllowed
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @GetMapping
    @RolesAllowed("ADMIN")
    fun getAll(): ResponseEntity<List<UserResponseDTO>> = ResponseEntity.ok(userService.getAll())

    @GetMapping("/{id}")
    @RolesAllowed("ADMIN")
    fun getById(@PathVariable id: Long): ResponseEntity<UserResponseDTO> =
        ResponseEntity.ok(userService.getById(id))

    @PostMapping
    @RolesAllowed("ADMIN")
    fun create(@RequestBody body: CreateUserRequestDTO): ResponseEntity<UserResponseDTO> =
        ResponseEntity.ok(userService.create(body))

    @PutMapping("/{id}")
    @RolesAllowed("ADMIN")
    fun update(@PathVariable id: Long, @RequestBody body: UpdateUserRequestDTO): ResponseEntity<UserResponseDTO> =
        ResponseEntity.ok(userService.update(id, body))

    @PatchMapping("/{id}/toggle-status")
    @RolesAllowed("ADMIN")
    fun toggleStatus(@PathVariable id: Long): ResponseEntity<UserResponseDTO> =
        ResponseEntity.ok(userService.toggleStatus(id))

    @DeleteMapping("/{id}")
    @RolesAllowed("ADMIN")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        userService.delete(id)
        return ResponseEntity.noContent().build()
    }
}