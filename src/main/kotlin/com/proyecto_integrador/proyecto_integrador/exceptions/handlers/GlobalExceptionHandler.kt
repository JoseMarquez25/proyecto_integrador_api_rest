package com.proyecto_integrador.exceptions.handlers

import com.proyecto_integrador.exceptions.*
import com.proyecto_integrador.models.responses.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.dao.DataIntegrityViolationException

@RestControllerAdvice
class GlobalExceptionHandler {

    // ============================
    // USER EXCEPTIONS
    // ============================
    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFound(ex: UserNotFoundException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(message = ex.message ?: "Usuario no encontrado.")
        return ResponseEntity(response, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(UsernameAlreadyExistsException::class)
    fun handleUsernameExists(ex: UsernameAlreadyExistsException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(message = ex.message ?: "El nombre de usuario ya está registrado.")
        return ResponseEntity(response, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(EmailAlreadyExistsException::class)
    fun handleEmailExists(ex: EmailAlreadyExistsException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(message = ex.message ?: "El correo electrónico ya está en uso.")
        return ResponseEntity(response, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(InvalidCredentialsException::class)
    fun handleInvalidCredentials(ex: InvalidCredentialsException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(message = ex.message ?: "Credenciales inválidas.")
        return ResponseEntity(response, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(UserSuperuserNullException::class)
    fun handleUserSuperuserNull(ex: UserSuperuserNullException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(message = ex.message ?: "El campo is_superuser no puede ser nulo.")
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    // ============================
    // VIDEO EXCEPTIONS
    // ============================
    @ExceptionHandler(VideoNotFoundException::class)
    fun handleVideoNotFound(ex: VideoNotFoundException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(message = ex.message ?: "Video no encontrado.")
        return ResponseEntity(response, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(VideoInactiveException::class)
    fun handleVideoInactive(ex: VideoInactiveException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(message = ex.message ?: "Video inactivo.")
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    // ============================
    // COURSE EXCEPTIONS
    // ============================
    @ExceptionHandler(CourseNotFoundException::class)
    fun handleCourseNotFound(ex: CourseNotFoundException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(message = ex.message ?: "Curso no encontrado.")
        return ResponseEntity(response, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(CourseSlugAlreadyExistsException::class)
    fun handleCourseSlugExists(ex: CourseSlugAlreadyExistsException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(message = ex.message ?: "Slug de curso ya existe.")
        return ResponseEntity(response, HttpStatus.CONFLICT)
    }

    // ============================
    // ENROLLMENT EXCEPTIONS
    // ============================
    @ExceptionHandler(EnrollmentNotFoundException::class)
    fun handleEnrollmentNotFound(ex: EnrollmentNotFoundException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(message = ex.message ?: "Inscripción no encontrada.")
        return ResponseEntity(response, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(EnrollmentAlreadyExistsException::class)
    fun handleEnrollmentExists(ex: EnrollmentAlreadyExistsException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(message = ex.message ?: "Inscripción ya existe.")
        return ResponseEntity(response, HttpStatus.CONFLICT)
    }

    // ============================
    // GENERIC EXCEPTIONS
    // ============================
    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFound(ex: ResourceNotFoundException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(message = ex.message ?: "Recurso no encontrado.")
        return ResponseEntity(response, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequest(ex: BadRequestException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(message = ex.message ?: "Solicitud inválida.")
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(UnauthorizedException::class)
    fun handleUnauthorized(ex: UnauthorizedException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(message = ex.message ?: "No autorizado.")
        return ResponseEntity(response, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(ForbiddenException::class)
    fun handleForbidden(ex: ForbiddenException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(message = ex.message ?: "Acceso prohibido.")
        return ResponseEntity(response, HttpStatus.FORBIDDEN)
    }

    // ============================
// EXTRA HANDLERS PARA ERRORES COMUNES
// ============================
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleJsonParseError(ex: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(
            message = "Verifica los datos ingresados: contienen errores de formato o valores nulos en campos obligatorios."
        )
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrity(ex: DataIntegrityViolationException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(
            message = "Los datos enviados violan restricciones de la base de datos. Asegúrate de completar todos los campos obligatorios."
        )
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

}
