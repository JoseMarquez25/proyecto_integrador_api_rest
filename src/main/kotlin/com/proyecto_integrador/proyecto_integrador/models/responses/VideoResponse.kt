package com.proyecto_integrador.models.responses

import java.time.LocalDateTime

data class VideoResponse(
    val id: Long,
    val title: String,
    val shortDescription: String?,
    val description: String?,
    val documentation: String?,
    val youtubeUrl: String,
    val instrumentId: Long?,
    val instructorId: Long,
    val level: String,
    val durationType: String,
    val isActive: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

