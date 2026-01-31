package com.proyecto_integrador.services

import com.proyecto_integrador.exceptions.EnrollmentAlreadyExistsException
import com.proyecto_integrador.exceptions.EnrollmentNotFoundException
import com.proyecto_integrador.exceptions.UserNotFoundException
import com.proyecto_integrador.exceptions.CourseNotFoundException
import com.proyecto_integrador.mappers.EnrollmentMapper
import com.proyecto_integrador.models.requests.CreateEnrollmentRequest
import com.proyecto_integrador.models.responses.EnrollmentResponse
import com.proyecto_integrador.repositories.EnrollmentRepository
import com.proyecto_integrador.repositories.UserRepository
import com.proyecto_integrador.repositories.CourseRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class EnrollmentService(
    private val enrollmentRepository: EnrollmentRepository,
    private val userRepository: UserRepository,
    private val courseRepository: CourseRepository,
    private val mapper: EnrollmentMapper = EnrollmentMapper()
) {

    fun findAll(): List<EnrollmentResponse> =
        enrollmentRepository.findAll().map { mapper.toResponse(it) }

    fun findById(id: Long): EnrollmentResponse {
        val enrollment = enrollmentRepository.findByIdOrNull(id)
            ?: throw EnrollmentNotFoundException(id)
        return mapper.toResponse(enrollment)
    }

    fun save(req: CreateEnrollmentRequest): EnrollmentResponse {
        val user = userRepository.findByIdOrNull(req.userId)
            ?: throw UserNotFoundException(req.userId)

        val course = courseRepository.findByIdOrNull(req.courseId)
            ?: throw CourseNotFoundException(req.courseId)

        if (enrollmentRepository.findByUserAndCourse(user, course).isPresent)
            throw EnrollmentAlreadyExistsException(req.userId, req.courseId)

        val entity = mapper.toEntity(req, user, course)
        val saved = enrollmentRepository.save(entity)
        return mapper.toResponse(saved)
    }

    fun delete(id: Long) {
        val existing = enrollmentRepository.findByIdOrNull(id)
            ?: throw EnrollmentNotFoundException(id)

        try {
            enrollmentRepository.delete(existing)
        } catch (ex: Exception) {
            throw IllegalStateException("No se puede eliminar la inscripción con ID $id. Puede tener registros dependientes.")
        }
    }
}
