package com.proyecto_integrador.mappers

import com.proyecto_integrador.models.entities.CourseEntity
import com.proyecto_integrador.models.entities.UserEntity
import com.proyecto_integrador.models.entities.VideoEntity
import com.proyecto_integrador.models.requests.CreateCourseRequest
import com.proyecto_integrador.models.responses.CourseResponse
import com.proyecto_integrador.proyecto_integrador.models.requests.UpdateCourseRequest

class CourseMapper {

    fun toEntity(req: CreateCourseRequest,
                 instructors: List<UserEntity>,
                 students: List<UserEntity>,
                 videos: List<VideoEntity>): CourseEntity =
        CourseEntity(
            title = req.title,
            slug = req.slug ?: req.title.lowercase().replace(" ", "-"),
            description = req.description,
            thumbnail = req.thumbnail,
            instructors = instructors.toMutableSet(),
            students = students.toMutableSet(),
            videos = videos.toMutableSet()
        )

    fun updateEntity(entity: CourseEntity,
                     req: UpdateCourseRequest,
                     instructors: List<UserEntity>?,
                     students: List<UserEntity>?,
                     videos: List<VideoEntity>?): CourseEntity {
        req.title?.let { entity.title = it }
        req.slug?.let { entity.slug = it }
        req.description?.let { entity.description = it }
        req.thumbnail?.let { entity.thumbnail = it }
        instructors?.let { entity.instructors = it.toMutableSet() }
        students?.let { entity.students = it.toMutableSet() }
        videos?.let { entity.videos = it.toMutableSet() }
        return entity
    }

    fun toResponse(entity: CourseEntity): CourseResponse =
        CourseResponse(
            id = entity.id!!,
            title = entity.title,
            slug = entity.slug,
            description = entity.description,
            thumbnail = entity.thumbnail,
            instructorIds = entity.instructors.map { it.id!! },
            studentIds = entity.students.map { it.id!! },
            videoIds = entity.videos.map { it.id!! },
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
}
