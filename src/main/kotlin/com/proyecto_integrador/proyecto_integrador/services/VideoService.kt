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
        val video = videoRepo.findById(id)
            .orElseThrow { RuntimeException("Video no encontrado") }

        req.title?.let { video.title = it }
        req.shortDescription?.let { video.shortDescription = it }
        req.description?.let { video.description = it }
        req.videoFile?.let { video.videoFile = it }
        req.documentation?.let { video.documentation = it }
        req.instructor?.let { video.instructor = it }
        req.level?.let { video.level = it }
        req.durationType?.let { video.durationType = it }
        req.isActive?.let { video.isActive = it }

        // updatedAt se actualiza automáticamente con @PreUpdate
        return mapper.toResponse(videoRepo.save(video))
    }


    fun deleteVideo(id: Long) {
        if (!videoRepo.existsById(id)) {
            throw Exception("Video no encontrado")
        }
        videoRepo.deleteById(id)
    }
}
