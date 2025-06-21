package ch.edutrack.api.service

import ch.edutrack.api.dto.CreateUserRequestDTO
import ch.edutrack.api.dto.UpdateUserRequestDTO
import ch.edutrack.api.dto.UserResponseDTO
import ch.edutrack.api.model.RoleEntity
import ch.edutrack.api.model.UserEntity
import ch.edutrack.api.repository.RoleRepository
import ch.edutrack.api.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun toggleStatus(id: Long): UserResponseDTO {
        val user = userRepository.findById(id)
            .orElseThrow { IllegalArgumentException("User not found") }

        user.isActive = !user.isActive
        return userRepository.save(user).toDTO()
    }

    fun getById(id: Long): UserResponseDTO {
        val user = userRepository.findById(id)
            .orElseThrow { IllegalArgumentException("User not found") }
        return user.toDTO()
    }

    fun getAll(): List<UserResponseDTO> = userRepository.findAllByIsActiveIsTrue().map { it.toDTO() }

    fun create(request: CreateUserRequestDTO): UserResponseDTO {
        val roles = roleRepository.findAllById(request.roleIds).toSet()
        if (roles.size != request.roleIds.size) {
            throw IllegalArgumentException("One or more roles not found")
        }

        val user = UserEntity(
            username = request.username,
            email = request.email,
            password = passwordEncoder.encode(request.password),
            firstName = request.firstName,
            lastName = request.lastName,
            roles = roles as MutableSet<RoleEntity>
        )

        return userRepository.save(user).toDTO()
    }

    fun update(id: Long, request: UpdateUserRequestDTO): UserResponseDTO {
        val user = userRepository.findById(id)
            .orElseThrow { IllegalArgumentException("User not found") }

        val roles = roleRepository.findAllById(request.roleIds).toSet()
        if (roles.size != request.roleIds.size) {
            throw IllegalArgumentException("One or more roles not found")
        }

        user.username = request.username
        user.email = request.email
        user.firstName = request.firstName
        user.lastName = request.lastName
        user.isActive = request.isActive
        user.roles = roles as MutableSet<RoleEntity>

        if (!request.password.isNullOrBlank()) {
            user.password = passwordEncoder.encode(request.password)
        }

        return userRepository.save(user).toDTO()
    }

    fun delete(userId: Long) {
        val user = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("User not found") }

        user.isActive = false
        userRepository.save(user)
    }


    private fun UserEntity.toDTO() = UserResponseDTO(
        id = id,
        username = username,
        email = email,
        firstName = firstName,
        lastName = lastName,
        isActive = isActive,
        roles = roles.map { it.name }
    )
}