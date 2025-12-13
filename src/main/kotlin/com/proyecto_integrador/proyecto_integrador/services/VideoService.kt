package com.proyecto_integrador.services

import com.proyecto_integrador.mappers.VideoMapper
import com.proyecto_integrador.models.entities.VideoEntity
import com.proyecto_integrador.models.requests.CreateVideoRequest
import com.proyecto_integrador.models.requests.UpdateVideoRequest
import com.proyecto_integrador.models.responses.VideoResponse
import com.proyecto_integrador.repositories.VideoRepository
import org.springframework.stereotype.Service

@Service
class VideoService(
    private val videoRepo: VideoRepository,
    private val mapper: VideoMapper = VideoMapper()
) {

    fun createVideo(req: CreateVideoRequest): VideoResponse {
        val entity = mapper.toEntity(req)
        val saved = videoRepo.save(entity)
        return mapper.toResponse(saved)
    }

    fun getAll(): List<VideoResponse> =
        videoRepo.findAll().map { mapper.toResponse(it) }

    fun getById(id: Long): VideoResponse =
        mapper.toResponse(videoRepo.findById(id).orElseThrow { Exception("Video no encontrado") })

    fun updateVideo(id: Long, req: UpdateVideoRequest): VideoResponse {
        val video = videoRepo.findById(id).orElseThrow { Exception("Video no encontrado") }

        val updated = video.copy(
            title = req.title ?: video.title,
            shortDescription = req.shortDescription ?: video.shortDescription,
            description = req.description ?: video.description,
            videoFile = req.videoFile ?: video.videoFile,
            documentation = req.documentation ?: video.documentation,
            instructor = req.instructor ?: video.instructor,
            level = req.level ?: video.level,
            durationType = req.durationType ?: video.durationType,
            isActive = req.isActive ?: video.isActive,
            updatedAt = java.time.LocalDateTime.now()
        )

        return mapper.toResponse(videoRepo.save(updated))
    }

    fun deleteVideo(id: Long) {
        if (!videoRepo.existsById(id)) {
            throw Exception("Video no encontrado")
        }
        videoRepo.deleteById(id)
    }
}
