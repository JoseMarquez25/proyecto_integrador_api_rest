package com.proyecto_integrador.services

import com.proyecto_integrador.exceptions.*
import com.proyecto_integrador.mappers.EnrollmentMapper
import com.proyecto_integrador.models.entities.CourseEntity
import com.proyecto_integrador.models.entities.EnrollmentEntity
import com.proyecto_integrador.models.entities.UserEntity
import com.proyecto_integrador.models.requests.CreateEnrollmentRequest
import com.proyecto_integrador.repositories.CourseRepository
import com.proyecto_integrador.repositories.EnrollmentRepository
import com.proyecto_integrador.repositories.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.*

class EnrollmentServiceTest {

    private lateinit var enrollmentRepository: EnrollmentRepository
    private lateinit var userRepository: UserRepository
    private lateinit var courseRepository: CourseRepository
    private lateinit var enrollmentService: EnrollmentService
    private val mapper = EnrollmentMapper()

    @BeforeEach
    fun setUp() {
        enrollmentRepository = mock(EnrollmentRepository::class.java)
        userRepository = mock(UserRepository::class.java)
        courseRepository = mock(CourseRepository::class.java)
        enrollmentService = EnrollmentService(enrollmentRepository, userRepository, courseRepository, mapper)
    }

    @Test
    fun `findAll should return list of enrollments`() {
        val user = UserEntity(username = "juan", email = "juan@test.com", password = "1234").apply { id = 1L }
        val course = CourseEntity(title = "Curso Kotlin", slug = "curso-kotlin").apply { id = 1L }
        val enrollment = EnrollmentEntity(user, course).apply { id = 1L }

        `when`(enrollmentRepository.findAll()).thenReturn(listOf(enrollment))

        val result = enrollmentService.findAll()

        assertEquals(1, result.size)
        assertEquals(1L, result[0].id)
    }

    @Test
    fun `findById should return enrollment when exists`() {
        val user = UserEntity("juan", "juan@test.com", "1234").apply { id = 1L }
        val course = CourseEntity("Curso Kotlin", "curso-kotlin").apply { id = 1L }
        val enrollment = EnrollmentEntity(user, course).apply { id = 1L }

        `when`(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment))

        val result = enrollmentService.findById(1L)

        assertEquals(1L, result.id)
        assertEquals(1L, result.userId)
        assertEquals(1L, result.courseId)
    }

    @Test
    fun `findById should throw EnrollmentNotFoundException when not exists`() {
        `when`(enrollmentRepository.findById(99L)).thenReturn(Optional.empty())

        assertThrows(EnrollmentNotFoundException::class.java) {
            enrollmentService.findById(99L)
        }
    }

    @Test
    fun `save should throw UserNotFoundException when user not exists`() {
        val request = CreateEnrollmentRequest(userId = 1L, courseId = 1L)

        `when`(userRepository.findById(1L)).thenReturn(Optional.empty())

        assertThrows(UserNotFoundException::class.java) {
            enrollmentService.save(request)
        }
    }

    @Test
    fun `save should throw CourseNotFoundException when course not exists`() {
        val request = CreateEnrollmentRequest(userId = 1L, courseId = 1L)
        val user = UserEntity("juan", "juan@test.com", "1234").apply { id = 1L }

        `when`(userRepository.findById(1L)).thenReturn(Optional.of(user))
        `when`(courseRepository.findById(1L)).thenReturn(Optional.empty())

        assertThrows(CourseNotFoundException::class.java) {
            enrollmentService.save(request)
        }
    }

    @Test
    fun `save should throw EnrollmentAlreadyExistsException when enrollment exists`() {
        val request = CreateEnrollmentRequest(userId = 1L, courseId = 1L)
        val user = UserEntity("juan", "juan@test.com", "1234").apply { id = 1L }
        val course = CourseEntity("Curso Kotlin", "curso-kotlin").apply { id = 1L }
        val enrollment = EnrollmentEntity(user, course)

        `when`(userRepository.findById(1L)).thenReturn(Optional.of(user))
        `when`(courseRepository.findById(1L)).thenReturn(Optional.of(course))
        `when`(enrollmentRepository.findByUserAndCourse(user, course)).thenReturn(Optional.of(enrollment))

        assertThrows(EnrollmentAlreadyExistsException::class.java) {
            enrollmentService.save(request)
        }
    }

    @Test
    fun `save should persist and return enrollment`() {
        val request = CreateEnrollmentRequest(userId = 1L, courseId = 1L)
        val user = UserEntity("juan", "juan@test.com", "1234").apply { id = 1L }
        val course = CourseEntity("Curso Kotlin", "curso-kotlin").apply { id = 1L }
        val enrollment = EnrollmentEntity(user, course).apply { id = 1L }

        `when`(userRepository.findById(1L)).thenReturn(Optional.of(user))
        `when`(courseRepository.findById(1L)).thenReturn(Optional.of(course))
        `when`(enrollmentRepository.findByUserAndCourse(user, course)).thenReturn(Optional.empty())
        `when`(enrollmentRepository.save(any(EnrollmentEntity::class.java))).thenReturn(enrollment)

        val result = enrollmentService.save(request)

        assertEquals(1L, result.id)
    }

    @Test
    fun `delete should throw EnrollmentNotFoundException when not exists`() {
        `when`(enrollmentRepository.findById(99L)).thenReturn(Optional.empty())

        assertThrows(EnrollmentNotFoundException::class.java) {
            enrollmentService.delete(99L)
        }
    }

    @Test
    fun `delete should throw IllegalStateException when repository fails`() {
        val user = UserEntity("juan", "juan@test.com", "1234").apply { id = 1L }
        val course = CourseEntity("Curso Kotlin", "curso-kotlin").apply { id = 1L }
        val enrollment = EnrollmentEntity(user, course).apply { id = 1L }

        `when`(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment))
        doThrow(RuntimeException("DB error")).`when`(enrollmentRepository).delete(enrollment)

        assertThrows(IllegalStateException::class.java) {
            enrollmentService.delete(1L)
        }
    }

    @Test
    fun `delete should remove enrollment when exists`() {
        val user = UserEntity("juan", "juan@test.com", "1234").apply { id = 1L }
        val course = CourseEntity("Curso Kotlin", "curso-kotlin").apply { id = 1L }
        val enrollment = EnrollmentEntity(user, course).apply { id = 1L }

        `when`(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment))

        enrollmentService.delete(1L)

        verify(enrollmentRepository, times(1)).delete(enrollment)
    }
}
