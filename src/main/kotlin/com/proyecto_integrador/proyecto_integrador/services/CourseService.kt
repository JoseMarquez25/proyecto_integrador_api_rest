package com.proyecto_integrador.services

import com.proyecto_integrador.exceptions.CourseNotFoundException
import com.proyecto_integrador.exceptions.CourseSlugAlreadyExistsException
import com.proyecto_integrador.mappers.CourseMapper
import com.proyecto_integrador.models.entities.CourseEntity
import com.proyecto_integrador.models.requests.CreateCourseRequest
import com.proyecto_integrador.models.responses.CourseResponse
import com.proyecto_integrador.proyecto_integrador.models.requests.UpdateCourseRequest
import com.proyecto_integrador.repositories.CourseRepository
import com.proyecto_integrador.repositories.UserRepository
import com.proyecto_integrador.repositories.VideoRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CourseService(
    private val courseRepository: CourseRepository,
    private val userRepository: UserRepository,
    private val videoRepository: VideoRepository,
    private val mapper: CourseMapper = CourseMapper()
) {

    fun findAll(): List<CourseResponse> =
        courseRepository.findAll().map { mapper.toResponse(it) }

    fun findById(id: Long): CourseResponse {
        val course = courseRepository.findByIdOrNull(id)
            ?: throw CourseNotFoundException(id)
        return mapper.toResponse(course)
    }

    private fun validateCourse(course: CourseEntity) {
        if (course.title.isBlank())
            throw IllegalArgumentException("El título del curso no puede estar vacío")
        if (course.slug.isBlank())
            throw IllegalArgumentException("El slug del curso no puede estar vacío")
    }

    fun save(req: CreateCourseRequest): CourseResponse {
        if (req.slug != null && courseRepository.findBySlug(req.slug).isPresent)
            throw CourseSlugAlreadyExistsException(req.slug)

        val instructors = userRepository.findAllById(req.instructorIds)
        val students = userRepository.findAllById(req.studentIds)
        val videos = videoRepository.findAllById(req.videoIds)

        val entity = mapper.toEntity(req, instructors, students, videos)
        validateCourse(entity)

        val saved = courseRepository.save(entity)
        return mapper.toResponse(saved)
    }

    fun update(id: Long, req: UpdateCourseRequest): CourseResponse {
        val existing = courseRepository.findByIdOrNull(id)
            ?: throw CourseNotFoundException(id)

        val instructors = req.instructorIds?.let { userRepository.findAllById(it) }
        val students = req.studentIds?.let { userRepository.findAllById(it) }
        val videos = req.videoIds?.let { videoRepository.findAllById(it) }

        val updatedEntity = mapper.updateEntity(existing, req, instructors, students, videos)
        validateCourse(updatedEntity)

        val saved = courseRepository.save(updatedEntity)
        return mapper.toResponse(saved)
    }

    fun delete(id: Long) {
        val existing = courseRepository.findByIdOrNull(id)
            ?: throw CourseNotFoundException(id)

        try {
            courseRepository.delete(existing)
        } catch (ex: Exception) {
            throw IllegalStateException("No se puede eliminar el curso con ID $id. Puede tener registros dependientes.")
        }
    }
}
