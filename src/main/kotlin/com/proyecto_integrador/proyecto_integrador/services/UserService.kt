package com.proyecto_integrador.services

import com.proyecto_integrador.exceptions.*
import com.proyecto_integrador.mappers.UserMapper
import com.proyecto_integrador.models.entities.UserEntity
import com.proyecto_integrador.models.requests.CreateUserRequest
import com.proyecto_integrador.models.requests.UpdateUserRequest
import com.proyecto_integrador.models.responses.UserResponse
import com.proyecto_integrador.repositories.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val mapper: UserMapper = UserMapper()
) {

    fun findAll(): List<UserResponse> =
        userRepository.findAll().map { mapper.toResponse(it) }

    fun findById(id: Long): UserResponse {
        val user = userRepository.findByIdOrNull(id)
            ?: throw UserNotFoundException(id)
        return mapper.toResponse(user)
    }

    private fun validateUser(user: UserEntity) {
        if (user.username.isBlank())
            throw IllegalArgumentException("El username no puede estar vacío")

        if (user.email.isBlank())
            throw IllegalArgumentException("El email no puede estar vacío")

        if (user.password.isBlank())
            throw IllegalArgumentException("La contraseña no puede estar vacía")

        if (user.isSuperuser == null) // aunque en entity es no-null, validamos
            throw UserSuperuserNullException()
    }

    fun save(req: CreateUserRequest): UserResponse {
        if (userRepository.findByUsername(req.username).isPresent)
            throw UsernameAlreadyExistsException(req.username)

        if (userRepository.findByEmail(req.email).isPresent)
            throw EmailAlreadyExistsException(req.email)

        val entity = mapper.toEntity(req)
        validateUser(entity)

        val saved = userRepository.save(entity)
        return mapper.toResponse(saved)
    }

    fun update(id: Long, req: UpdateUserRequest): UserResponse {
        val existing = userRepository.findByIdOrNull(id)
            ?: throw UserNotFoundException(id)

        val updatedEntity = mapper.updateEntity(existing, req)
        validateUser(updatedEntity)

        val saved = userRepository.save(updatedEntity)
        return mapper.toResponse(saved)
    }

    fun delete(id: Long) {
        val existing = userRepository.findByIdOrNull(id)
            ?: throw UserNotFoundException(id)

        try {
            userRepository.delete(existing)
        } catch (ex: Exception) {
            throw IllegalStateException("No se puede eliminar el usuario con ID $id. Puede tener registros dependientes.")
        }
    }
}
