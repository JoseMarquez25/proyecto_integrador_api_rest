package com.proyecto_integrador.controllers

import com.proyecto_integrador.models.requests.CreateEnrollmentRequest
import com.proyecto_integrador.models.responses.EnrollmentResponse
import com.proyecto_integrador.services.EnrollmentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/enrollments")
class EnrollmentController(
    private val service: EnrollmentService
) {

    @GetMapping
    fun findAll(): ResponseEntity<List<EnrollmentResponse>> =
        ResponseEntity.ok(service.findAll())

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<EnrollmentResponse> =
        ResponseEntity.ok(service.findById(id))

    @PostMapping
    fun save(@RequestBody req: CreateEnrollmentRequest): ResponseEntity<EnrollmentResponse> =
        ResponseEntity.ok(service.save(req))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Map<String, String>> {
        service.delete(id)
        val response = mapOf("message" to "Inscripción eliminada correctamente")
        return ResponseEntity(response, HttpStatus.OK)
    }

}
