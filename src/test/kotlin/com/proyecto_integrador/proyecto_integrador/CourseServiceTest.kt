package com.proyecto_integrador.services

import com.proyecto_integrador.exceptions.CourseNotFoundException
import com.proyecto_integrador.exceptions.CourseSlugAlreadyExistsException
import com.proyecto_integrador.mappers.CourseMapper
import com.proyecto_integrador.models.entities.CourseEntity
import com.proyecto_integrador.models.requests.CreateCourseRequest
import com.proyecto_integrador.proyecto_integrador.models.requests.UpdateCourseRequest
import com.proyecto_integrador.repositories.CourseRepository
import com.proyecto_integrador.repositories.UserRepository
import com.proyecto_integrador.repositories.VideoRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.*

class CourseServiceTest {

    private lateinit var courseRepository: CourseRepository
    private lateinit var userRepository: UserRepository
    private lateinit var videoRepository: VideoRepository
    private lateinit var courseService: CourseService
    private val mapper = CourseMapper()

    @BeforeEach
    fun setUp() {
        courseRepository = mock(CourseRepository::class.java)
        userRepository = mock(UserRepository::class.java)
        videoRepository = mock(VideoRepository::class.java)
        courseService = CourseService(courseRepository, userRepository, videoRepository, mapper)
    }

    @Test
    fun `findAll should return list of courses`() {
        val courses = listOf(
            CourseEntity(title = "Curso Kotlin", slug = "curso-kotlin").apply { id = 1L },
            CourseEntity(title = "Curso Spring", slug = "curso-spring").apply { id = 2L }
        )

        `when`(courseRepository.findAll()).thenReturn(courses)

        val result = courseService.findAll()

        assertEquals(2, result.size)
        assertEquals("Curso Kotlin", result[0].title)
        assertEquals("Curso Spring", result[1].title)
    }

    @Test
    fun `findById should return course when exists`() {
        val course = CourseEntity(title = "Curso Kotlin", slug = "curso-kotlin").apply { id = 1L }

        `when`(courseRepository.findById(1L)).thenReturn(Optional.of(course))

        val result = courseService.findById(1L)

        assertEquals("Curso Kotlin", result.title)
        assertEquals("curso-kotlin", result.slug)
    }

    @Test
    fun `findById should throw CourseNotFoundException when not exists`() {
        `when`(courseRepository.findById(99L)).thenReturn(Optional.empty())

        assertThrows(CourseNotFoundException::class.java) {
            courseService.findById(99L)
        }
    }

    @Test
    fun `save should throw CourseSlugAlreadyExistsException when slug exists`() {
        val request = CreateCourseRequest(
            title = "Curso Kotlin",
            slug = "curso-kotlin",
            instructorIds = listOf(1L),
            studentIds = listOf(2L),
            videoIds = listOf(3L)
        )

        `when`(courseRepository.findBySlug("curso-kotlin"))
            .thenReturn(Optional.of(CourseEntity("Curso Kotlin", "curso-kotlin")))

        assertThrows(CourseSlugAlreadyExistsException::class.java) {
            courseService.save(request)
        }
    }

    @Test
    fun `save should throw IllegalArgumentException when title is blank`() {
        val request = CreateCourseRequest(
            title = "",
            slug = "curso-kotlin",
            instructorIds = listOf(),
            studentIds = listOf(),
            videoIds = listOf()
        )

        `when`(courseRepository.findBySlug("curso-kotlin")).thenReturn(Optional.empty())

        assertThrows(IllegalArgumentException::class.java) {
            courseService.save(request)
        }
    }

    @Test
    fun `save should persist and return course including optional relations`() {
        val request = CreateCourseRequest(
            title = "Curso Kotlin",
            slug = "curso-kotlin",
            instructorIds = listOf(10L),
            studentIds = listOf(20L),
            videoIds = listOf(30L)
        )

        val savedEntity = CourseEntity(title = "Curso Kotlin", slug = "curso-kotlin").apply { id = 1L }

        `when`(courseRepository.findBySlug("curso-kotlin")).thenReturn(Optional.empty())
        // Ensure the optional relation lookups are executed (return empty lists is fine)
        `when`(userRepository.findAllById(anyList())).thenReturn(listOf())
        `when`(videoRepository.findAllById(anyList())).thenReturn(listOf())
        `when`(courseRepository.save(any(CourseEntity::class.java))).thenReturn(savedEntity)

        val result = courseService.save(request)

        assertEquals(1L, result.id)
        assertEquals("Curso Kotlin", result.title)
    }

    @Test
    fun `update should throw CourseNotFoundException when course not exists`() {
        val request = UpdateCourseRequest(title = "Nuevo título")

        `when`(courseRepository.findById(99L)).thenReturn(Optional.empty())

        assertThrows(CourseNotFoundException::class.java) {
            courseService.update(99L, request)
        }
    }

    @Test
    fun `update should throw IllegalArgumentException when existing course slug is blank`() {
        // Simulate an existing course with blank slug to hit the branch that throws
        val existing = CourseEntity(title = "Curso X", slug = "").apply { id = 1L }
        val request = UpdateCourseRequest(title = "Nuevo título")

        `when`(courseRepository.findById(1L)).thenReturn(Optional.of(existing))

        assertThrows(IllegalArgumentException::class.java) {
            courseService.update(1L, request)
        }
    }

    @Test
    fun `update should throw CourseSlugAlreadyExistsException when new slug already exists`() {
        val existing = CourseEntity(title = "Curso Kotlin", slug = "curso-kotlin").apply { id = 1L }
        val request = UpdateCourseRequest(slug = "curso-duplicado")

        `when`(courseRepository.findById(1L)).thenReturn(Optional.of(existing))

        // Simulamos que ya existe otro curso con el slug "curso-duplicado" y con id distinto (2L)
        val other = CourseEntity(title = "Otro Curso", slug = "curso-duplicado").apply { id = 2L }
        `when`(courseRepository.findBySlug("curso-duplicado")).thenReturn(Optional.of(other))

        // Stub de seguridad para save: si por error se llama, que devuelva la entidad pasada
        `when`(courseRepository.save(any(CourseEntity::class.java)))
            .thenAnswer { invocation -> invocation.arguments[0] as CourseEntity }

        // Ejecutamos y esperamos la excepción
        assertThrows(CourseSlugAlreadyExistsException::class.java) {
            courseService.update(1L, request)
        }

        // Aseguramos que no se intentó persistir
        verify(courseRepository, never()).save(any(CourseEntity::class.java))
    }



    @Test
    fun `update should persist and return updated course when optional lists and slug provided`() {
        val existing = CourseEntity(title = "Curso Kotlin", slug = "curso-kotlin").apply { id = 1L }

        val request = UpdateCourseRequest(
            title = "Curso Kotlin Avanzado",
            slug = "curso-kotlin-avanzado",
            instructorIds = listOf(11L, 12L),
            studentIds = listOf(21L),
            videoIds = listOf(31L, 32L)
        )

        `when`(courseRepository.findById(1L)).thenReturn(Optional.of(existing))
        // new slug does not exist
        `when`(courseRepository.findBySlug("curso-kotlin-avanzado")).thenReturn(Optional.empty())
        // ensure optional lookups are executed; return empty lists to simulate found entities
        `when`(userRepository.findAllById(anyList())).thenReturn(listOf())
        `when`(videoRepository.findAllById(anyList())).thenReturn(listOf())
        `when`(courseRepository.save(any(CourseEntity::class.java))).thenAnswer { invocation ->
            val arg = invocation.arguments[0] as CourseEntity
            arg.apply { id = 1L }
        }

        val result = courseService.update(1L, request)

        assertEquals(1L, result.id)
        assertEquals("Curso Kotlin Avanzado", result.title)
        assertEquals("curso-kotlin-avanzado", result.slug)
    }

    @Test
    fun `update should persist and return updated course when only title provided`() {
        val existing = CourseEntity(title = "Curso Kotlin", slug = "curso-kotlin").apply { id = 1L }
        val request = UpdateCourseRequest(title = "Nuevo título")

        `when`(courseRepository.findById(1L)).thenReturn(Optional.of(existing))
        `when`(courseRepository.save(any(CourseEntity::class.java))).thenReturn(existing.apply { title = "Nuevo título" })

        val result = courseService.update(1L, request)

        assertEquals("Nuevo título", result.title)
    }

    @Test
    fun `update should allow slug change when new slug does not exist`() {
        val existing = CourseEntity(title = "Curso Kotlin", slug = "curso-kotlin").apply { id = 1L }
        val request = UpdateCourseRequest(slug = "curso-nuevo")

        `when`(courseRepository.findById(1L)).thenReturn(Optional.of(existing))
        `when`(courseRepository.findBySlug("curso-nuevo")).thenReturn(Optional.empty())
        `when`(courseRepository.save(any(CourseEntity::class.java)))
            .thenAnswer { invocation -> (invocation.arguments[0] as CourseEntity).apply { id = 1L } }

        val result = courseService.update(1L, request)

        assertEquals("curso-nuevo", result.slug)
    }

    @Test
    fun `update should allow slug when found course has same id`() {
        val existing = CourseEntity(title = "Curso Kotlin", slug = "curso-kotlin").apply { id = 1L }
        val request = UpdateCourseRequest(slug = "curso-kotlin")

        `when`(courseRepository.findById(1L)).thenReturn(Optional.of(existing))
        // Simulamos que el repositorio devuelve el mismo curso
        `when`(courseRepository.findBySlug("curso-kotlin")).thenReturn(Optional.of(existing))
        `when`(courseRepository.save(any(CourseEntity::class.java)))
            .thenAnswer { invocation -> (invocation.arguments[0] as CourseEntity).apply { id = 1L } }

        val result = courseService.update(1L, request)

        assertEquals("curso-kotlin", result.slug)
    }

    @Test
    fun `update should allow when slug is null`() {
        val existing = CourseEntity(title = "Curso Kotlin", slug = "curso-kotlin").apply { id = 1L }
        val request = UpdateCourseRequest(title = "Nuevo título", slug = null)

        `when`(courseRepository.findById(1L)).thenReturn(Optional.of(existing))
        `when`(courseRepository.save(any(CourseEntity::class.java)))
            .thenAnswer { invocation -> (invocation.arguments[0] as CourseEntity).apply { id = 1L } }

        val result = courseService.update(1L, request)

        assertEquals("Nuevo título", result.title)
    }



    @Test
    fun `delete should throw CourseNotFoundException when course not exists`() {
        `when`(courseRepository.findById(99L)).thenReturn(Optional.empty())

        assertThrows(CourseNotFoundException::class.java) {
            courseService.delete(99L)
        }
    }

    @Test
    fun `delete should throw IllegalStateException when repository fails`() {
        val course = CourseEntity(title = "Curso Kotlin", slug = "curso-kotlin").apply { id = 1L }

        `when`(courseRepository.findById(1L)).thenReturn(Optional.of(course))
        doThrow(RuntimeException("DB error")).`when`(courseRepository).delete(course)

        assertThrows(IllegalStateException::class.java) {
            courseService.delete(1L)
        }
    }

    @Test
    fun `delete should remove course when exists`() {
        val course = CourseEntity(title = "Curso Kotlin", slug = "curso-kotlin").apply { id = 1L }

        `when`(courseRepository.findById(1L)).thenReturn(Optional.of(course))

        courseService.delete(1L)

        verify(courseRepository, times(1)).delete(course)
    }

    @Test
    fun `update should work when slug is null`() {
        val existing = CourseEntity(title = "Curso Kotlin", slug = "curso-kotlin").apply { id = 1L }
        val request = UpdateCourseRequest(title = "Nuevo título", slug = null)

        `when`(courseRepository.findById(1L)).thenReturn(Optional.of(existing))
        `when`(courseRepository.save(any(CourseEntity::class.java)))
            .thenAnswer { invocation -> (invocation.arguments[0] as CourseEntity).apply { id = 1L } }

        val result = courseService.update(1L, request)

        assertEquals("Nuevo título", result.title)
        assertEquals("curso-kotlin", result.slug) // el slug original se mantiene
    }

}

