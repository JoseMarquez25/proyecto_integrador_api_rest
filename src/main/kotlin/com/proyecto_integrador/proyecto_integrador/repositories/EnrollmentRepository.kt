package com.proyecto_integrador.repositories

import com.proyecto_integrador.models.entities.EnrollmentEntity
import com.proyecto_integrador.models.entities.UserEntity
import com.proyecto_integrador.models.entities.CourseEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface EnrollmentRepository : JpaRepository<EnrollmentEntity, Long> {
    fun findByUserAndCourse(user: UserEntity, course: CourseEntity): Optional<EnrollmentEntity>
    fun findAllByUser(user: UserEntity): List<EnrollmentEntity>
    fun findAllByCourse(course: CourseEntity): List<EnrollmentEntity>
}
