package com.proyecto_integrador.repositories

import com.proyecto_integrador.models.entities.CourseEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface CourseRepository : JpaRepository<CourseEntity, Long> {
    fun findBySlug(slug: String): Optional<CourseEntity>
}
