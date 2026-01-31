package com.proyecto_integrador.models.responses

import java.time.LocalDateTime

data class EnrollmentResponse(
    val id: Long,
    val userId: Long,
    val courseId: Long,
    val enrolledAt: LocalDateTime,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
