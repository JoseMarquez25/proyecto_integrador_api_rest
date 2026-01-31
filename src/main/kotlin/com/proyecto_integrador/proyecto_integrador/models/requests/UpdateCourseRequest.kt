package com.proyecto_integrador.proyecto_integrador.models.requests

data class UpdateCourseRequest(
    val title: String? = null,
    val slug: String? = null,
    val description: String? = null,
    val thumbnail: String? = null,
    val instructorIds: List<Long>? = null,
    val studentIds: List<Long>? = null,
    val videoIds: List<Long>? = null
)