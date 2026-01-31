package com.proyecto_integrador.services

import com.proyecto_integrador.exceptions.VideoNotFoundException
import com.proyecto_integrador.mappers.VideoMapper
import com.proyecto_integrador.models.entities.VideoEntity
import com.proyecto_integrador.models.requests.CreateVideoRequest
import com.proyecto_integrador.models.requests.UpdateVideoRequest
import com.proyecto_integrador.repositories.VideoRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.*

class VideoServiceTest {

    private lateinit var videoRepository: VideoRepository
    private lateinit var videoService: VideoService
    private val mapper = VideoMapper()

    @BeforeEach
    fun setUp() {
        videoRepository = mock(VideoRepository::class.java)
        videoService = VideoService(videoRepository, mapper)
    }

    @Test
    fun `findAll should return list of videos`() {
        val videos = listOf(
            VideoEntity(
                title = "Video Kotlin",
                youtubeUrl = "http://youtube.com/1",
                level = "beginner",
                durationType = "short"
            ).apply { id = 1L },
            VideoEntity(
                title = "Video Spring",
                youtubeUrl = "http://youtube.com/2",
                level = "advanced",
                durationType = "long"
            ).apply { id = 2L }
        )

        `when`(videoRepository.findAll()).thenReturn(videos)

        val result = videoService.findAll()

        assertEquals(2, result.size)
        assertEquals("Video Kotlin", result[0].title)
        assertEquals("Video Spring", result[1].title)
    }

    @Test
    fun `findById should return video when exists`() {
        val video = VideoEntity(
            title = "Video Kotlin",
            youtubeUrl = "http://youtube.com/1",
            level = "beginner",
            durationType = "short"
        ).apply { id = 1L }

        `when`(videoRepository.findById(1L)).thenReturn(Optional.of(video))

        val result = videoService.findById(1L)

        assertEquals("Video Kotlin", result.title)
        assertEquals("http://youtube.com/1", result.youtubeUrl)
    }

    @Test
    fun `findById should throw VideoNotFoundException when not exists`() {
        `when`(videoRepository.findById(99L)).thenReturn(Optional.empty())

        assertThrows(VideoNotFoundException::class.java) {
            videoService.findById(99L)
        }
    }

    @Test
    fun `save should throw IllegalArgumentException when title is blank`() {
        val request = CreateVideoRequest(
            title = "",
            shortDescription = "Intro a Kotlin",
            description = "Curso básico de Kotlin",
            documentation = "http://docs.com/kotlin",
            youtubeUrl = "http://youtube.com/1",
            instrumentId = 1L,
            instructorId = 1L,
            level = "beginner",
            durationType = "short",
            isActive = true
        )

        assertThrows(IllegalArgumentException::class.java) {
            videoService.save(request)
        }
    }

    @Test
    fun `save should throw IllegalArgumentException when youtubeUrl is blank`() {
        val request = CreateVideoRequest(
            title = "Video Kotlin",
            shortDescription = "Intro a Kotlin",
            description = "Curso básico de Kotlin",
            documentation = "http://docs.com/kotlin",
            youtubeUrl = "",
            instrumentId = 1L,
            instructorId = 1L,
            level = "beginner",
            durationType = "short",
            isActive = true
        )

        assertThrows(IllegalArgumentException::class.java) {
            videoService.save(request)
        }
    }


    @Test
    fun `save should persist and return video`() {
        val request = CreateVideoRequest(
            title = "Video Kotlin",
            shortDescription = "Intro a Kotlin",
            description = "Curso básico de Kotlin",
            documentation = "http://docs.com/kotlin",
            youtubeUrl = "http://youtube.com/1",
            instrumentId = 1L,
            instructorId = 1L,
            level = "beginner",
            durationType = "short",
            isActive = true
        )

        val savedEntity = VideoEntity(
            title = "Video Kotlin",
            shortDescription = "Intro a Kotlin",
            description = "Curso básico de Kotlin",
            documentation = "http://docs.com/kotlin",
            youtubeUrl = "http://youtube.com/1",
            instrumentId = 1L,
            instructorId = 1L,
            level = "beginner",
            durationType = "short",
            isActive = true
        ).apply { id = 1L }

        `when`(videoRepository.save(any(VideoEntity::class.java))).thenReturn(savedEntity)

        val result = videoService.save(request)

        assertEquals(1L, result.id)
        assertEquals("Video Kotlin", result.title)
    }


    @Test
    fun `update should throw VideoNotFoundException when video not exists`() {
        val request = UpdateVideoRequest(title = "Nuevo título")

        `when`(videoRepository.findById(99L)).thenReturn(Optional.empty())

        assertThrows(VideoNotFoundException::class.java) {
            videoService.update(99L, request)
        }
    }

    @Test
    fun `update should throw IllegalArgumentException when level is blank`() {
        val existing = VideoEntity(
            title = "Video Kotlin",
            youtubeUrl = "http://youtube.com/1",
            level = "beginner",
            durationType = "short"
        ).apply { id = 1L }

        val request = UpdateVideoRequest(level = "")

        `when`(videoRepository.findById(1L)).thenReturn(Optional.of(existing))

        assertThrows(IllegalArgumentException::class.java) {
            videoService.update(1L, request)
        }
    }

    @Test
    fun `update should persist and return updated video`() {
        val existing = VideoEntity(
            title = "Video Kotlin",
            youtubeUrl = "http://youtube.com/1",
            level = "beginner",
            durationType = "short"
        ).apply { id = 1L }

        val request = UpdateVideoRequest(title = "Nuevo título", youtubeUrl = "http://youtube.com/new")

        `when`(videoRepository.findById(1L)).thenReturn(Optional.of(existing))
        `when`(videoRepository.save(any(VideoEntity::class.java)))
            .thenReturn(existing.apply { title = "Nuevo título"; youtubeUrl = "http://youtube.com/new" })

        val result = videoService.update(1L, request)

        assertEquals("Nuevo título", result.title)
        assertEquals("http://youtube.com/new", result.youtubeUrl)
    }

    @Test
    fun `delete should throw VideoNotFoundException when not exists`() {
        `when`(videoRepository.findById(99L)).thenReturn(Optional.empty())

        assertThrows(VideoNotFoundException::class.java) {
            videoService.delete(99L)
        }
    }

    @Test
    fun `delete should throw IllegalStateException when repository fails`() {
        val video = VideoEntity(
            title = "Video Kotlin",
            youtubeUrl = "http://youtube.com/1",
            level = "beginner",
            durationType = "short"
        ).apply { id = 1L }

        `when`(videoRepository.findById(1L)).thenReturn(Optional.of(video))
        doThrow(RuntimeException("DB error")).`when`(videoRepository).delete(video)

        assertThrows(IllegalStateException::class.java) {
            videoService.delete(1L)
        }
    }

    @Test
    fun `delete should remove video when exists`() {
        val video = VideoEntity(
            title = "Video Kotlin",
            youtubeUrl = "http://youtube.com/1",
            level = "beginner",
            durationType = "short"
        ).apply { id = 1L }

        `when`(videoRepository.findById(1L)).thenReturn(Optional.of(video))

        videoService.delete(1L)

        verify(videoRepository, times(1)).delete(video)
    }
}
