package com.proyecto_integrador.models.entities

import jakarta.persistence.*

@Entity
@Table(name = "academy_video")
class VideoEntity(

    var title: String,

    @Column(name = "short_description")
    var shortDescription: String? = null,

    var description: String? = null,

    var documentation: String? = null,

    @Column(name = "youtube_url")
    var youtubeUrl: String,   // reemplaza videoFile por youtubeUrl

    // Relaciones
    @Column(name = "instrument_id")
    var instrumentId: Long? = null,   // relación con Instrument (nullable)

    @Column(name = "instructor_id")
    var instructorId: Long = 1,       // relación con User

    var level: String = "nivel1",

    @Column(name = "duration_type")
    var durationType: String = "medium",

    @Column(name = "is_active")
    var isActive: Boolean = true

) : BaseEntity()
