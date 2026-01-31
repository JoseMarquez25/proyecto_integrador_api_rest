package com.proyecto_integrador.controllers

import com.proyecto_integrador.models.requests.CreateCourseRequest
import com.proyecto_integrador.models.responses.CourseResponse
import com.proyecto_integrador.proyecto_integrador.models.requests.UpdateCourseRequest
import com.proyecto_integrador.services.CourseService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/courses")
class CourseController(
    private val service: CourseService
) {

    @GetMapping
    fun findAll(): ResponseEntity<List<CourseResponse>> =
        ResponseEntity.ok(service.findAll())

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<CourseResponse> =
        ResponseEntity.ok(service.findById(id))

    @PostMapping
    fun save(@RequestBody req: CreateCourseRequest): ResponseEntity<CourseResponse> =
        ResponseEntity.ok(service.save(req))

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody req: UpdateCourseRequest): ResponseEntity<CourseResponse> =
        ResponseEntity.ok(service.update(id, req))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Map<String, String>> {
        service.delete(id)
        val response = mapOf("message" to "Curso eliminado correctamente")
        return ResponseEntity(response, HttpStatus.OK)
    }

}
