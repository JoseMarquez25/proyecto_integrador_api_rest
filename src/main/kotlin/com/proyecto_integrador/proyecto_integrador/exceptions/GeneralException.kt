package com.proyecto_integrador.exceptions

class ResourceNotFoundException(resource: String, id: Long) :
    RuntimeException("No se encontró el recurso $resource con id $id.")

class BadRequestException(message: String) :
    RuntimeException("Solicitud inválida: $message.")

class UnauthorizedException :
    RuntimeException("No autorizado para realizar esta acción.")

class ForbiddenException :
    RuntimeException("Acceso prohibido a este recurso.")
