package com.proyecto_integrador.models.responses

import java.time.LocalDateTime

data class CourseResponse(
    val id: Long,
    val title: String,
    val slug: String,
    val description: String?,
    val thumbnail: String?,
    val instructorIds: List<Long>,
    val studentIds: List<Long>,
    val videoIds: List<Long>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
