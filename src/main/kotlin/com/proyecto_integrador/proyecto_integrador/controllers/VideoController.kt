package com.proyecto_integrador.controllers

import com.proyecto_integrador.models.requests.CreateVideoRequest
import com.proyecto_integrador.models.requests.UpdateVideoRequest
import com.proyecto_integrador.models.responses.VideoResponse
import com.proyecto_integrador.services.VideoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/videos")
class VideoController(
    private val service: VideoService
) {

    @GetMapping
    fun findAll(): ResponseEntity<List<VideoResponse>> =
        ResponseEntity.ok(service.findAll())

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<VideoResponse> =
        ResponseEntity.ok(service.findById(id))

    @PostMapping
    fun save(@RequestBody req: CreateVideoRequest): ResponseEntity<VideoResponse> =
        ResponseEntity.ok(service.save(req))

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody req: UpdateVideoRequest): ResponseEntity<VideoResponse> =
        ResponseEntity.ok(service.update(id, req))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Map<String, String>> {
        service.delete(id)
        val response = mapOf("message" to "Video eliminado correctamente")
        return ResponseEntity(response, HttpStatus.OK)
    }

}
