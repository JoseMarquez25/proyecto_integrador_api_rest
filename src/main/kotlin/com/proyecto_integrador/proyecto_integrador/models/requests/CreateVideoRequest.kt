package com.proyecto_integrador.models.requests

data class CreateVideoRequest(
    val title: String,
    val shortDescription: String?,
    val description: String?,
    val videoFile: String,
    val documentation: String?,
    val instructor: Long,
    val level: String,
    val durationType: String,
    val isActive: Boolean
)
