package com.proyecto_integrador.models.requests

data class CreateEnrollmentRequest(
    val userId: Long,
    val courseId: Long
)
