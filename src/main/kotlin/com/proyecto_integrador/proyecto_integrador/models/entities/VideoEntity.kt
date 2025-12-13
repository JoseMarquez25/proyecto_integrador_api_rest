package com.proyecto_integrador.models.entities

import jakarta.persistence.*

@Entity
@Table(name = "videos")
class VideoEntity(

    var title: String,

    @Column(name = "short_description")
    var shortDescription: String? = null,

    var description: String? = null,

    @Column(name = "video_file")
    var videoFile: String,

    var documentation: String? = null,

    var instructor: Long = 1,

    var level: String = "nivel1",

    @Column(name = "duration_type")
    var durationType: String = "medium",

    @Column(name = "is_active")
    var isActive: Boolean = true

) : BaseEntity()

