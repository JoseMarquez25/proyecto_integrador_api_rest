package com.proyecto_integrador.models.responses

import java.time.LocalDate
import java.time.LocalDateTime

data class UserResponse(
    val id: Long,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val role: String,
    val phone: String?,
    val birthDate: LocalDate?,
    val profileImage: String?,
    val bio: String?,
    val isActive: Boolean,
    val isSuperuser: Boolean,
    val isStaff: Boolean,
    val lastLogin: LocalDateTime?,
    val dateJoined: LocalDateTime,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
