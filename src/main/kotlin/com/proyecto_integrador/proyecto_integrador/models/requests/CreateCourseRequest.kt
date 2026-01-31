package com.proyecto_integrador.models.requests

data class CreateCourseRequest(
    val title: String,
    val slug: String? = null,
    val description: String? = null,
    val thumbnail: String? = null,
    val instructorIds: List<Long> = emptyList(),
    val studentIds: List<Long> = emptyList(),
    val videoIds: List<Long> = emptyList()
)

