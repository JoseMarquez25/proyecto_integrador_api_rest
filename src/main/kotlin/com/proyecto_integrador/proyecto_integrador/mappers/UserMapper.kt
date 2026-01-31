package com.proyecto_integrador.mappers

import com.proyecto_integrador.models.entities.UserEntity
import com.proyecto_integrador.models.requests.CreateUserRequest
import com.proyecto_integrador.models.requests.UpdateUserRequest
import com.proyecto_integrador.models.responses.UserResponse
import java.time.LocalDate
import java.time.LocalDateTime

class UserMapper {

    fun toEntity(req: CreateUserRequest): UserEntity =
        UserEntity(
            username = req.username,
            password = req.password,
            email = req.email,
            firstName = req.firstName,
            lastName = req.lastName,
            role = req.role,
            phone = req.phone,
            birthDate = req.birthDate?.let { LocalDate.parse(it) },
            profileImage = req.profileImage,
            bio = req.bio,
            isActive = req.isActive,
            isSuperuser = req.isSuperuser,
            isStaff = req.isStaff,
            lastLogin = null,
            dateJoined = LocalDateTime.now()
        )

    fun updateEntity(entity: UserEntity, req: UpdateUserRequest): UserEntity {
        req.username?.let { entity.username = it }
        req.password?.let { entity.password = it }
        req.email?.let { entity.email = it }
        req.firstName?.let { entity.firstName = it }
        req.lastName?.let { entity.lastName = it }
        req.role?.let { entity.role = it }
        req.phone?.let { entity.phone = it }
        req.birthDate?.let { entity.birthDate = LocalDate.parse(it) }
        req.profileImage?.let { entity.profileImage = it }
        req.bio?.let { entity.bio = it }
        req.isActive?.let { entity.isActive = it }
        req.isSuperuser?.let { entity.isSuperuser = it }
        req.isStaff?.let { entity.isStaff = it }
        req.lastLogin?.let { entity.lastLogin = LocalDateTime.parse(it) }
        return entity
    }

    fun toResponse(entity: UserEntity): UserResponse =
        UserResponse(
            id = entity.id!!,
            username = entity.username,
            email = entity.email,
            firstName = entity.firstName,
            lastName = entity.lastName,
            role = entity.role,
            phone = entity.phone,
            birthDate = entity.birthDate,
            profileImage = entity.profileImage,
            bio = entity.bio,
            isActive = entity.isActive,
            isSuperuser = entity.isSuperuser,
            isStaff = entity.isStaff,
            lastLogin = entity.lastLogin,
            dateJoined = entity.dateJoined,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
}
