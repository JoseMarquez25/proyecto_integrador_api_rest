package com.proyecto_integrador.controllers

import com.proyecto_integrador.models.requests.CreateUserRequest
import com.proyecto_integrador.models.requests.UpdateUserRequest
import com.proyecto_integrador.models.responses.UserResponse
import com.proyecto_integrador.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val service: UserService
) {

    @GetMapping
    fun findAll(): ResponseEntity<List<UserResponse>> =
        ResponseEntity.ok(service.findAll())

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<UserResponse> =
        ResponseEntity.ok(service.findById(id))

    @PostMapping
    fun save(@RequestBody req: CreateUserRequest): ResponseEntity<UserResponse> =
        ResponseEntity.ok(service.save(req))

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody req: UpdateUserRequest): ResponseEntity<UserResponse> =
        ResponseEntity.ok(service.update(id, req))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Map<String, String>> {
        service.delete(id)
        val response = mapOf("message" to "Usuario eliminado correctamente")
        return ResponseEntity(response, HttpStatus.OK)
    }

}
