package com.proyecto_integrador.exceptions

class UserNotFoundException(id: Long) :
    RuntimeException("No existe un usuario registrado con id $id.")

class UsernameAlreadyExistsException(username: String) :
    RuntimeException("Ya existe un usuario registrado con username $username.")

class EmailAlreadyExistsException(email: String) :
    RuntimeException("Ya existe un usuario registrado con email $email.")

class InvalidCredentialsException :
    RuntimeException("Credenciales inválidas. Verifique su usuario y contraseña.")
