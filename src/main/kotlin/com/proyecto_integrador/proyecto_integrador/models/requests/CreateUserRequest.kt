package com.proyecto_integrador.models.requests

data class CreateUserRequest(
    val username: String,
    val password: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val role: String = "student",
    val phone: String? = null,
    val birthDate: String? = null, // formato ISO yyyy-MM-dd
    val profileImage: String? = null,
    val bio: String? = null,
    val isActive: Boolean = true,
    val isSuperuser: Boolean = false,
    val isStaff: Boolean = false
)
