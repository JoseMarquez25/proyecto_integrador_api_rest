package com.proyecto_integrador.exceptions

class UserSuperuserNullException : RuntimeException(
    "El campo is_superuser no puede ser nulo. Debe especificarse explícitamente (true/false)."
)
