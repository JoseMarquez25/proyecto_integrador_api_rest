package com.proyecto_integrador.models.requests

data class UpdateVideoRequest(
    val title: String? = null,
    val shortDescription: String? = null,
    val description: String? = null,
    val documentation: String? = null,
    val youtubeUrl: String? = null,
    val instrumentId: Long? = null,
    val instructorId: Long? = null,
    val level: String? = null,
    val durationType: String? = null,
    val isActive: Boolean? = null
)

