package com.proyecto_integrador.models.entities

import jakarta.persistence.*

@Entity
@Table(name = "courses_course")
class CourseEntity(

    @Column(nullable = false)
    var title: String,

    @Column(unique = true)
    var slug: String,

    var description: String? = null,
    var thumbnail: String? = null,

    @ManyToMany
    @JoinTable(
        name = "courses_course_instructors",
        joinColumns = [JoinColumn(name = "course_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    var instructors: MutableSet<UserEntity> = mutableSetOf(),

    @ManyToMany
    @JoinTable(
        name = "courses_course_students",
        joinColumns = [JoinColumn(name = "course_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    var students: MutableSet<UserEntity> = mutableSetOf(),

    @ManyToMany
    @JoinTable(
        name = "courses_course_videos",
        joinColumns = [JoinColumn(name = "course_id")],
        inverseJoinColumns = [JoinColumn(name = "video_id")]
    )
    var videos: MutableSet<VideoEntity> = mutableSetOf()

) : BaseEntity()
