package com.proyecto_integrador.mappers

import com.proyecto_integrador.models.entities.VideoEntity
import com.proyecto_integrador.models.requests.CreateVideoRequest
import com.proyecto_integrador.models.responses.VideoResponse
import java.time.LocalDateTime

class VideoMapper {

    fun toEntity(req: CreateVideoRequest): VideoEntity =
        VideoEntity(
            title = req.title,
            shortDescription = req.shortDescription,
            description = req.description,
            videoFile = req.videoFile,
            documentation = req.documentation,
            instructor = req.instructor,
            level = req.level,
            durationType = req.durationType,
            isActive = req.isActive
        )

    fun toResponse(entity: VideoEntity): VideoResponse =
        VideoResponse(
            id = entity.id,
            title = entity.title,
            shortDescription = entity.shortDescription,
            description = entity.description,
            videoFile = entity.videoFile,
            documentation = entity.documentation,
            instructor = entity.instructor,
            level = entity.level,
            durationType = entity.durationType,
            isActive = entity.isActive,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
}
