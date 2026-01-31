package com.proyecto_integrador.exceptions

class VideoNotFoundException(id: Long) :
    RuntimeException("No existe un video registrado con id $id.")

class VideoInactiveException(id: Long) :
    RuntimeException("El video con id $id está inactivo y no puede reproducirse.")
