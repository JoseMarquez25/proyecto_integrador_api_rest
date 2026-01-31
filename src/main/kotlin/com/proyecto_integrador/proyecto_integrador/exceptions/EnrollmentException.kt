package com.proyecto_integrador.exceptions

class EnrollmentNotFoundException(id: Long) :
    RuntimeException("No existe una inscripción registrada con id $id.")

class EnrollmentAlreadyExistsException(userId: Long, courseId: Long) :
    RuntimeException("El usuario con id $userId ya está inscrito en el curso con id $courseId.")
