package com.proyecto_integrador.exceptions

class CourseNotFoundException(id: Long) :
    RuntimeException("No existe un curso registrado con id $id.")

class CourseSlugAlreadyExistsException(slug: String) :
    RuntimeException("Ya existe un curso registrado con slug $slug.")
