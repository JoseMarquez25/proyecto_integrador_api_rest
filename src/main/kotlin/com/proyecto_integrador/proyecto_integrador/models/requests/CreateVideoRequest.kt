package com.proyecto_integrador.models.requests

data class CreateVideoRequest(
    val title: String,
    val shortDescription: String?,
    val description: String?,
    val documentation: String?,
    val youtubeUrl: String,
    val instrumentId: Long?,
    val instructorId: Long,
    val level: String,
    val durationType: String,
    val isActive: Boolean
)
