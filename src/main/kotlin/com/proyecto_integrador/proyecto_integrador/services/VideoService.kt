package com.proyecto_integrador.services

import com.proyecto_integrador.exceptions.VideoNotFoundException
import com.proyecto_integrador.mappers.VideoMapper
import com.proyecto_integrador.models.entities.VideoEntity
import com.proyecto_integrador.models.requests.CreateVideoRequest
import com.proyecto_integrador.models.requests.UpdateVideoRequest
import com.proyecto_integrador.models.responses.VideoResponse
import com.proyecto_integrador.repositories.VideoRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class VideoService(
    private val videoRepository: VideoRepository,
    private val mapper: VideoMapper = VideoMapper()
) {

    fun findAll(): List<VideoResponse> =
        videoRepository.findAll().map { mapper.toResponse(it) }

    fun findById(id: Long): VideoResponse {
        val video = videoRepository.findByIdOrNull(id)
            ?: throw VideoNotFoundException(id)
        return mapper.toResponse(video)
    }

    private fun validateVideo(video: VideoEntity) {
        if (video.title.isBlank())
            throw IllegalArgumentException("El título del video no puede estar vacío")

        if (video.youtubeUrl.isBlank())
            throw IllegalArgumentException("La URL de YouTube no puede estar vacía")

        if (video.level.isBlank())
            throw IllegalArgumentException("El nivel del video no puede estar vacío")

        if (video.durationType.isBlank())
            throw IllegalArgumentException("El tipo de duración no puede estar vacío")
    }

    fun save(req: CreateVideoRequest): VideoResponse {
        val entity = mapper.toEntity(req)
        validateVideo(entity)
        val saved = videoRepository.save(entity)
        return mapper.toResponse(saved)
    }

    fun update(id: Long, req: UpdateVideoRequest): VideoResponse {
        val existing = videoRepository.findByIdOrNull(id)
            ?: throw VideoNotFoundException(id)

        // Actualizamos campos opcionales
        req.title?.let { existing.title = it }
        req.shortDescription?.let { existing.shortDescription = it }
        req.description?.let { existing.description = it }
        req.documentation?.let { existing.documentation = it }
        req.youtubeUrl?.let { existing.youtubeUrl = it }
        req.instrumentId?.let { existing.instrumentId = it }
        req.instructorId?.let { existing.instructorId = it }
        req.level?.let { existing.level = it }
        req.durationType?.let { existing.durationType = it }
        req.isActive?.let { existing.isActive = it }

        validateVideo(existing)
        val updated = videoRepository.save(existing)
        return mapper.toResponse(updated)
    }

    fun delete(id: Long) {
        val existing = videoRepository.findByIdOrNull(id)
            ?: throw VideoNotFoundException(id)

        try {
            videoRepository.delete(existing)
        } catch (ex: Exception) {
            throw IllegalStateException("No se puede eliminar el video con ID $id. Puede tener registros dependientes.")
        }
    }
}
