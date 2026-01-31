package com.proyecto_integrador.models.requests

data class UpdateUserRequest(
    val username: String? = null,
    val password: String? = null,
    val email: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val role: String? = null,
    val phone: String? = null,
    val birthDate: String? = null,
    val profileImage: String? = null,
    val bio: String? = null,
    val isActive: Boolean? = null,
    val isSuperuser: Boolean? = null,
    val isStaff: Boolean? = null,
    val lastLogin: String? = null
)
