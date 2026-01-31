package com.proyecto_integrador.models.entities

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "academy_user")
class UserEntity(

    @Column(nullable = false, unique = true)
    var username: String,

    @Column(nullable = false)
    var password: String,

    @Column(nullable = false, unique = true)
    var email: String,

    @Column(name = "first_name", nullable = false)
    var firstName: String = "",

    @Column(name = "last_name", nullable = false)
    var lastName: String = "",

    @Column(name = "is_staff", nullable = false)
    var isStaff: Boolean = false,

    @Column(name = "last_login")
    var lastLogin: LocalDateTime? = null,

    @Column(name = "date_joined", nullable = false)
    var dateJoined: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var role: String = "student", // admin, instructor, student

    var phone: String? = null,
    var birthDate: LocalDate? = null,
    var profileImage: String? = null,
    var bio: String? = null,

    @Column(name = "is_active")
    var isActive: Boolean = true,

    @Column(name = "is_superuser", nullable = false)
    var isSuperuser: Boolean = false

) : BaseEntity()
