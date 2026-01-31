package com.proyecto_integrador.mappers


import com.proyecto_integrador.models.entities.UserEntity
import com.proyecto_integrador.models.entities.CourseEntity
import com.proyecto_integrador.models.entities.EnrollmentEntity
import com.proyecto_integrador.models.requests.CreateEnrollmentRequest
import com.proyecto_integrador.models.responses.EnrollmentResponse

class EnrollmentMapper {

    fun toEntity(req: CreateEnrollmentRequest, user: UserEntity, course: CourseEntity): EnrollmentEntity =
        EnrollmentEntity(
            user = user,
            course = course
        )

    fun toResponse(entity: EnrollmentEntity): EnrollmentResponse =
        EnrollmentResponse(
            id = entity.id!!,
            userId = entity.user.id!!,
            courseId = entity.course.id!!,
            enrolledAt = entity.enrolledAt,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )

}
