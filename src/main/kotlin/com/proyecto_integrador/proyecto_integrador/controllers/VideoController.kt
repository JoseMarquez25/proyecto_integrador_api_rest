package com.proyecto_integrador.controllers

import com.proyecto_integrador.models.requests.CreateVideoRequest
import com.proyecto_integrador.models.requests.UpdateVideoRequest
import com.proyecto_integrador.models.responses.VideoResponse
import com.proyecto_integrador.services.VideoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/videos")
class VideoController(
    private val service: VideoService
) {

    @PostMapping
    fun create(@RequestBody req: CreateVideoRequest): ResponseEntity<VideoResponse> =
        ResponseEntity.ok(service.createVideo(req))

    @GetMapping
    fun getAll(): ResponseEntity<List<VideoResponse>> =
        ResponseEntity.ok(service.getAll())

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<VideoResponse> =
        ResponseEntity.ok(service.getById(id))

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody req: UpdateVideoRequest): ResponseEntity<VideoResponse> =
        ResponseEntity.ok(service.updateVideo(id, req))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        service.deleteVideo(id)
        return ResponseEntity.noContent().build()
    }
}
