package com.proyecto_integrador.models.entities

import com.proyecto_integrador.models.entities.*
import java.time.LocalDateTime

import jakarta.persistence.*

@Entity
@Table(
    name = "courses_enrollment",
    uniqueConstraints = [UniqueConstraint(columnNames = ["user_id", "course_id"])]
)
class EnrollmentEntity(

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserEntity,

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    var course: CourseEntity,

    @Column(name = "enrolled_at", nullable = false)
    var enrolledAt: LocalDateTime = LocalDateTime.now()

) : BaseEntity()
