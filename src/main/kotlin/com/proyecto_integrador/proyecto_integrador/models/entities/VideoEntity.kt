package com.proyecto_integrador.models.entities

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "videos")
data class VideoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val title: String,

    @Column(name = "short_description")
    val shortDescription: String? = null,

    val description: String? = null,

    @Column(name = "video_file")
    val videoFile: String,

    val documentation: String? = null,

    val instructor: Long = 1, // ID numérico del instructor (luego se mapeará con FK real)

    val level: String = "nivel1",

    @Column(name = "duration_type")
    val durationType: String = "medium",

    @Column(name = "is_active")
    val isActive: Boolean = true,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
